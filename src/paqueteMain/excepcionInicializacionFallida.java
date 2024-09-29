package paqueteMain;
public class excepcionInicializacionFallida extends Exception {
    excepcionInicializacionFallida(){
        super("La inicializacion no ha podido reslizarse, intente nuevamente");
    }
}
