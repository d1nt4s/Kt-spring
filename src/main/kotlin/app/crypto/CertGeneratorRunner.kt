package app.crypto

import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import java.io.File

@Component
class CertGeneratorRunner : CommandLineRunner {
    override fun run(vararg args: String?) {
        val dir = File("certs")
        val p12 = File(dir, "server.p12")
        val crt = File(dir, "server.crt")
        if (p12.exists() && crt.exists()) return

        val password = "password".toCharArray()
        val gen = CertUtils.generateSelfSignedRsa(
            cn = "localhost",
            sans = listOf("localhost", "127.0.0.1"),
            daysValid = 365,
            keyPassword = password
        )
        CertUtils.savePkcs12(gen.keystore, p12, password)
        CertUtils.saveCertPem(gen.cert, crt)
        println("[TLS] Generated: ${p12.absolutePath} , ${crt.absolutePath}")
    }
}
