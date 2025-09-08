package app.crypto

import org.bouncycastle.asn1.x500.X500Name
import org.bouncycastle.asn1.x509.*
import org.bouncycastle.cert.X509v3CertificateBuilder
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder
import java.io.File
import java.io.FileOutputStream
import java.math.BigInteger
import java.security.*
import java.security.cert.X509Certificate
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*

object CertUtils {
    init {
        if (Security.getProvider("BC") == null) {
            Security.addProvider(BouncyCastleProvider())
        }
    }

    data class Generated(
        val keystore: KeyStore,
        val cert: X509Certificate
    )

    fun generateSelfSignedRsa(
        cn: String,
        sans: List<String> = listOf(cn),
        daysValid: Long = 365,
        keyPassword: CharArray = "password".toCharArray()
    ): Generated {
        val kp = KeyPairGenerator.getInstance("RSA").apply { initialize(2048) }.genKeyPair()

        val now = Date.from(Instant.now())
        val notAfter = Date.from(Instant.now().plus(daysValid, ChronoUnit.DAYS))
        val subject = X500Name("CN=$cn, O=Dev, OU=Local, C=RS")
        val serial = BigInteger.valueOf(System.currentTimeMillis())

        val spki = SubjectPublicKeyInfo.getInstance(kp.public.encoded)
        val builder = X509v3CertificateBuilder(
            subject, serial, now, notAfter, subject, spki
        )

        builder.addExtension(Extension.basicConstraints, true, BasicConstraints(false))
        builder.addExtension(Extension.keyUsage, false, KeyUsage(KeyUsage.digitalSignature or KeyUsage.keyEncipherment))

        val sanNames = sans.map {
            val type = if (it.matches(Regex("\\d+\\.\\d+\\.\\d+\\.\\d+"))) GeneralName.iPAddress else GeneralName.dNSName
            GeneralName(type, it)
        }.toTypedArray()
        builder.addExtension(Extension.subjectAlternativeName, false, GeneralNames(sanNames))

        val signer = JcaContentSignerBuilder("SHA256withRSA").setProvider("BC").build(kp.private)
        val holder = builder.build(signer)
        val cert = JcaX509CertificateConverter().setProvider("BC").getCertificate(holder)

        // PKCS#12: кладём приватный ключ + цепочку (у нас одна)
        val ks = KeyStore.getInstance("PKCS12").apply { load(null, null) }
        ks.setKeyEntry("server-key", kp.private, keyPassword, arrayOf(cert))

        return Generated(ks, cert)
    }

    fun savePkcs12(keystore: KeyStore, file: File, password: CharArray) {
        file.parentFile?.mkdirs()
        FileOutputStream(file).use { keystore.store(it, password) }
    }

    fun saveCertPem(cert: X509Certificate, file: File) {
        file.parentFile?.mkdirs()
        val base64 = Base64.getMimeEncoder(64, "\n".toByteArray()).encodeToString(cert.encoded)
        file.writeText("-----BEGIN CERTIFICATE-----\n$base64\n-----END CERTIFICATE-----\n")
    }
}
