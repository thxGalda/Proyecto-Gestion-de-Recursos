package paqueteMain;
public class excepcionContrasena extends Exception {
    excepcionContrasena() {
        super("Contraseña incorrecta, ingrese nuevamente");
    }
}
