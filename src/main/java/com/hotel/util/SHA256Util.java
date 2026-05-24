package com.hotel.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Utilidad para encriptar contraseñas usando SHA-256.
 * Se usa en el login y al registrar nuevos usuarios.
 */
public class SHA256Util {

    /**
     * Constructor privado — clase de utilidad, no se instancia.
     */
    private SHA256Util() {}

    /**
     * Convierte un texto plano a su hash SHA-256 en formato hexadecimal.
     *
     * @param texto Contraseña en texto plano
     * @return Hash SHA-256 de 64 caracteres en minusculas
     */
    public static String encriptar(String texto) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(texto.getBytes(StandardCharsets.UTF_8));
            return bytesAHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al encriptar con SHA-256: " + e.getMessage());
        }
    }

    /**
     * Convierte un arreglo de bytes a su representacion hexadecimal.
     *
     * @param bytes Arreglo de bytes del hash
     * @return Cadena hexadecimal de 64 caracteres
     */
    private static String bytesAHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    /**
     * Verifica si una contraseña en texto plano coincide con un hash guardado.
     *
     * @param textoPlano     Contraseña ingresada por el usuario
     * @param hashGuardado   Hash almacenado en la base de datos
     * @return true si coinciden, false si no
     */
    public static boolean verificar(String textoPlano, String hashGuardado) {
        return encriptar(textoPlano).equals(hashGuardado);
    }
}
