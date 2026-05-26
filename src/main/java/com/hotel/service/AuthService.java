package com.hotel.service;

import com.hotel.dao.UsuarioDAO;
import com.hotel.model.UsuarioSistema;
import com.hotel.util.SHA256Util;

/**
 * Servicio de autenticación y gestión de credenciales de los usuarios del sistema.
 * <p>
 * Actúa como capa de servicio entre la interfaz de usuario y el {@link UsuarioDAO},
 * encapsulando la lógica de verificación de contraseñas mediante hash SHA-256.
 * </p>
 * <p>
 * Patrón de diseño: <b>Service Layer</b> — centraliza la lógica de negocio relacionada
 * con la autenticación, delegando el acceso a datos al {@link UsuarioDAO}.
 * </p>
 */
public class AuthService {

    /**
     * Objeto de acceso a datos utilizado para consultar y actualizar usuarios del sistema.
     */
    private final UsuarioDAO dao = new UsuarioDAO();

    /**
     * Autentica a un usuario verificando su contraseña contra el hash almacenado en la base de datos.
     *
     * @param usuario  el nombre de usuario (login) a autenticar.
     * @param password la contraseña en texto plano proporcionada por el usuario.
     * @return el objeto {@link UsuarioSistema} si las credenciales son válidas,
     *         o {@code null} si el usuario no existe o la contraseña es incorrecta.
     */
    public UsuarioSistema login(String usuario, String password) {
        UsuarioSistema u = dao.buscarPorUsuario(usuario);
        if (u == null) return null;
        return SHA256Util.verificar(password, u.getPasswordHash()) ? u : null;
    }

    /**
     * Busca y retorna un usuario del sistema por su nombre de usuario.
     *
     * @param usuario el nombre de usuario a buscar.
     * @return el {@link UsuarioSistema} encontrado, o {@code null} si no existe.
     */
    public UsuarioSistema buscarUsuario(String usuario) {
        return dao.buscarPorUsuario(usuario);
    }

    /**
     * Cambia la contraseña de un usuario existente en el sistema.
     * <p>
     * La nueva contraseña se encripta con SHA-256 antes de persistirse en la base de datos.
     * </p>
     *
     * @param usuario        el nombre de usuario cuya contraseña se desea cambiar.
     * @param nuevaPassword  la nueva contraseña en texto plano.
     * @return {@code true} si el cambio fue exitoso; {@code false} si el usuario no existe.
     */
    public boolean cambiarPassword(String usuario, String nuevaPassword) {
        UsuarioSistema u = dao.buscarPorUsuario(usuario);
        if (u == null) return false;
        String nuevoHash = SHA256Util.encriptar(nuevaPassword);
        u.setPasswordHash(nuevoHash);
        dao.actualizar(u);
        return true;
    }
}