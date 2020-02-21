package LinkedListsStacksAndQueues;

public class Main {

    public static void main(String[] args) {

        /*
        Mensagens msg = new Mensagens();
        CircularArrayQueue<Integer> collection = new CircularArrayQueue<>();
        collection.enqueue(1);
        collection.enqueue(5);
        collection.enqueue(2);

        String a = msg.encode("Ola o meu nome e martim", collection);

        System.out.println("codificado: "+a);


        CircularArrayQueue<Integer> collection2 = new CircularArrayQueue<>();
        collection2.enqueue(1);
        collection2.enqueue(5);
        collection2.enqueue(2);

        System.out.println("descodificado: " + msg.decode(a, collection2));




        LinkedQueue<Comparable> a1 = new LinkedQueue<>();
        LinkedQueue<Comparable> a2 = new LinkedQueue<>();

        for(int i = 0; i<20; i+=3)a1.enqueue(i);
        for(int i = 0; i<50; i+=5)a2.enqueue(i);

        LinkedQueue<Comparable> a3 = (LinkedQueue<Comparable>) ordenar2Queues(a1,a2);

        while (!a3.isEmpty())System.out.println(a3.dequeue());

*/

        /*OrdenadorClasse<String> ordenador = new OrdenadorClasse<>();
System.out.println("AA".compareTo("AAAAAA"));
        QueueADT<QueueADT<String>> A = ordenador.Ex5(new String[]{"A", "BB", "CCC", "DDDD", "EEEEE", "FFFFFF", "GGGGGGG"});

        QueueADT<String> B =  A.dequeue();
         System.out.println(B.first().getClass());
         int sizeOfQueue = B.size();
         for (int i = 0; i<sizeOfQueue; i++){
             System.out.println(B.dequeue());
         }

    }

      Dadas  N strings, criar N Queues cada    uma    contendo    uma    das    strings.
  De seguida criar uma Queue das N Queues.
  Repetidamente     aplicar     uma     operacao de junção ordenada às primeiras duas Queues e  re-­‐inserir
  a    nova    Queue no    final.    Repetir    até    que    a    Queue contenha    apenas    uma Queue.
     */
/*

        QueueADT<Teste> filaDeTestes = new LinkedQueue<>();

        for (int i = 0; i < 20; i++) {
            if (i % 3 == 0) {
                filaDeTestes.enqueue(new Teste(i));
            }
        }

        QueueADT<Teste> filaDeTestes2 = new LinkedQueue<>();

        for (int i = 0; i < 25; i++) {
            if (i % 4 == 0) {
                filaDeTestes2.enqueue(new Teste(i));
            }
        }

        OrdenadorClasse<Teste> a  = new OrdenadorClasse<>();

        QueueADT<Teste> B = a.ordenar2Queues(filaDeTestes, filaDeTestes2);

        while (!B.isEmpty()){
            System.out.println(B.dequeue().getId());
        }
    }

*/
    }
}

