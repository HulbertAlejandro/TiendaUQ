package recursividad;

public class Exercises {

    //Ejemplo suma de N numeros enteros
    public static int sumaN_Enteros(int n) {
        if(n == 1) {
            return 1;
        } else {
            return sumaN_Enteros(n - 1) + n;
        }
    }

    public static void imprimirMatrioshka(int n){
        if(n == 1){
            System.out.println("Se abrio la muñeca: "+ n);
        } else {
            System.out.println("Se abrio la muñeca: "+ n);
            imprimirMatrioshka(n - 1);
        }
        System.out.println("Se cerro la muñeca: "+ n);
    }

    public static int encontrarNumeroMayor(int []arreglo, int inicio, int fin){
        if(inicio == fin){
            return arreglo[inicio];
        }

        int medio = (inicio + fin)/2;
        int mayorIzquierda = encontrarNumeroMayor(arreglo, inicio, medio);
        int mayorDerecha = encontrarNumeroMayor(arreglo, medio + 1, fin);

        return Math.max(mayorIzquierda, mayorDerecha);
    }

    public static int mulplicarArreglo(int []arreglo, int inicio, int fin){
        if(inicio == fin){
            return arreglo[inicio];
        }

        int medio = (inicio + fin)/2;
        int resultadoIzquierda = mulplicarArreglo(arreglo, inicio, medio);
        int resultadoDerecha = mulplicarArreglo(arreglo, medio + 1, fin);

        return resultadoIzquierda * resultadoDerecha;
    }

    // Método recursivo para buscar un elemento en un arreglo ordenado
    public static int busquedaBinariaRecursiva(int[] arreglo, int elemento, int inicio, int fin) {
        // Si el inicio es mayor que el fin, significa que el elemento no está presente en el arreglo
        if (inicio > fin) {
            return -1;
        }

        // Calcula el punto medio del rango
        int medio = (inicio +fin) / 2;

        // Si el elemento en el punto medio es igual al elemento buscado, retorna el índice del punto medio
        if (arreglo[medio] == elemento) {
            return medio;
        } else if (arreglo[medio] < elemento) {
            // Si el elemento en el punto medio es menor que el elemento buscado, busca en la mitad derecha del arreglo
            return busquedaBinariaRecursiva(arreglo, elemento, medio + 1, fin);
        } else {
            // Si el elemento en el punto medio es mayor que el elemento buscado, busca en la mitad izquierda del arreglo
            return busquedaBinariaRecursiva(arreglo, elemento, inicio, medio - 1);
        }
    }

    public static void main(String []args)
    {
        int n = 5;
        System.out.println("La suma de n numeros enteros es: " +sumaN_Enteros(n) + "\n");

        imprimirMatrioshka(n);

        int[] arreglo = {2, 4, 6, 8, 10};
        System.out.println("\nEl numero mayor es: " + encontrarNumeroMayor(arreglo, 0, arreglo.length -1));
        System.out.println("\nEl resultado es: " + mulplicarArreglo(arreglo, 0, arreglo.length -1));

        int elemento = 10;
        int resultado = busquedaBinariaRecursiva(arreglo, elemento, 0, arreglo.length);
        if (resultado != -1) {
            System.out.println("\nEl elemento " + elemento + " está en la posición " + resultado + ".");
        } else {
            System.out.println("\nEl elemento " + elemento + " no está presente en el arreglo.");
        }

    }
}
