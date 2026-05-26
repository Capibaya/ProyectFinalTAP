package com.hotel.service;

import com.hotel.dao.UsuarioDAO;
import com.hotel.model.UsuarioSistema;
import com.hotel.util.SHA256Util;

public class AuthService {

    private final UsuarioDAO dao = new UsuarioDAO();

    public UsuarioSistema login(String usuario, String password) {
        UsuarioSistema u = dao.buscarPorUsuario(usuario);
        if (u == null) return null;
        return SHA256Util.verificar(password, u.getPasswordHash()) ? u : null;
    }

    /**
     * Busca un usuario por su nombre de usuario (sin verificar contraseña).
     * Usado en el flujo de recuperación de contraseña.
     *
     * @param usuario Nombre de usuario
     * @return UsuarioSistema si existe, null si no
     */
    public UsuarioSistema buscarUsuario(String usuario) {
        return dao.buscarPorUsuario(usuario);
    }

    /**
     * Cambia la contraseña de un usuario por nombre de usuario.
     * Hashea la nueva contraseña con SHA-256 antes de guardarla.
     *
     * @param usuario         Nombre de usuario
     * @param nuevaPassword   Nueva contraseña en texto plano
     * @return true si se actualizó correctamente, false si hubo error
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