package paqueteMain;
public class excepcionDuplicacionRecurso extends Exception {
    excepcionDuplicacionRecurso(){
        super("El archivo ya se encuentra");
    }
}
