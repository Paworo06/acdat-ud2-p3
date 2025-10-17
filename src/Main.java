import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Main {

    public static void main(String[] args) {
        if (args.length < 3) {
            imprimirUso();
            return;
        }

        File base = new File(args[0]);
        if (!base.exists() || !base.isDirectory()) {
            System.err.println("La ruta base debe existir y ser un directorio.");
            return;
        }

        String operacion = args[1];

        try {
            switch (operacion) {
                case "borrar":
                    String rutaRel = args[2];
                    borrarSeguro(base, rutaRel); // TODO implementar
                    break;

                case "renombrar":
                    if (args.length < 4) {
                        System.err.println("Faltan <origenRel> <destinoRel>.");
                        return;
                    }
                    String origenRel = args[2];
                    String destinoRel = args[3];
                    renombrarSeguro(base, origenRel, destinoRel); // TODO implementar
                    break;

                case "permiso":
                    if (args.length < 5) {
                        System.err.println("Faltan <rutaRel> <permiso> <on|off>.");
                        return;
                    }
                    String ruta = args[2];
                    String permiso = args[3].toLowerCase();
                    String valor = args[4].toLowerCase();
                    boolean activar;

                    switch (valor) {
                        case "on":
                        case "true":
                            activar = true;
                            break;
                        case "off":
                        case "false":
                            activar = false;
                            break;
                        default:
                            System.err.println("El valor debe ser on|off o true|false.");
                            return;
                    }

                    cambiarPermiso(base, ruta, permiso, activar); // TODO implementar
                    break;

                default:
                    System.err.println("Operación no reconocida: " + operacion);
                    imprimirUso();
            }
        } catch (IOException e) {
            System.err.println("[main] IOException: " + e.getMessage());
        }
    }

    private static void imprimirUso() {
        System.err.println("Uso:");
        System.err.println("  java MiniAdminSwitch <rutaBase> borrar <rutaRel>");
        System.err.println("  java MiniAdminSwitch <rutaBase> renombrar <origenRel> <destinoRel>");
        System.err.println("  java MiniAdminSwitch <rutaBase> permiso <rutaRel> <lectura|escritura|ejecucion> <on|off>");
    }

    // TODO: borrarSeguro
    // 1. Crear File objetivo = new File(base, rutaRel)
    // 2. Si no existe -> lanzar IOException("No existe")
    // 3. Si es directorio:
    //    - listar contenido
    //    - si null -> lanzar IOException("No se pudo listar")
    //    - si tiene elementos -> lanzar IOException("Directorio no vacío")
    // 4. Intentar borrar con delete()
    //    - si true -> mostrar mensaje de éxito
    //    - si false -> lanzar IOException("No se pudo borrar")
    // 5. Capturar SecurityException y envolver en IOException
    // 6. finally -> mostrar mensaje "[borrar] Fin"
    private static void borrarSeguro(File base, String rutaRel) throws IOException {
        // TODO implementar
        File f = new File(base, rutaRel);

        try {
            if (!f.exists()) {
                throw new IOException("No existe");
            } else if(f.isDirectory()) {
                String[] listado = f.list();

                if (listado.length == 0) {
                    throw new IOException("No se pudo listar");
                } else {
                    for (String elemento : listado) {
                        System.out.println(elemento);
                    }
                    throw new IOException("Directorio no vacío");
                }
            } else {
                if  (f.delete()) {
                    System.out.println("El archivo se ha borrado correctamente.");
                } else {
                    throw new IOException("No se pudo borrar");
                }
            }
        } catch (SecurityException se){
            throw new IOException("Error de seguridad");
        } finally{
            System.out.println("[borrar] Fin");
        }
    }

    // TODO: renombrarSeguro
    // 1. Crear File origen y destino
    // 2. Si origen no existe -> mensaje de error
    // 3. Si destino existe -> mensaje de error
    // 4. Si el directorio padre del destino no existe -> mensaje de error
    // 5. Usar renameTo()
    //    - si true -> mensaje de éxito
    //    - si false -> mensaje de fallo
    // 6. Capturar SecurityException
    // 7. finally -> mensaje "[renombrar] Fin"
    private static void renombrarSeguro(File base, String origenRel, String destinoRel) {
        // TODO implementar
        File origen = new File(base, origenRel);
        File destino = new File(base, destinoRel);

        try {
            if (!origen.exists()) {
                System.err.println("El fichero origen no existe");
            } else if(destino.exists()) {
                System.err.println("El fichero destino ya existe");
            } else if (origen.getParent() == null) {
                System.err.println("El directorio padre del fichero de destino no existe");
            } else {
                if (origen.renameTo(destino)) {
                    System.out.println("Buena bro");
                } else {
                    System.out.println("Espabila bro");
                }
            }
        } catch (SecurityException se) {
            System.err.println("Error de seguridad");
        } finally {
            System.out.println("[renombrar] Fin");
        }
    }

    // TODO: cambiarPermiso
    // 1. Crear File f = new File(base, rutaRel)
    // 2. Si no existe -> mensaje de error
    // 3. Usar switch(permiso):
    //    - lectura   -> setReadable(activar, false)
    //    - escritura -> setWritable(activar, false)
    //    - ejecucion -> setExecutable(activar, false)
    //    - otro -> mensaje "Permiso no válido"
    // 4. Mostrar resultado según boolean devuelto
    // 5. Capturar SecurityException
    // 6. finally -> mensaje "[permiso] Fin"
    private static void cambiarPermiso(File base, String rutaRel, String permiso, boolean activar) {
        // TODO implementar
        System.err.println("TODO cambiarPermiso (" + permiso + " -> " + (activar ? "on" : "off") + ")");
    }
}