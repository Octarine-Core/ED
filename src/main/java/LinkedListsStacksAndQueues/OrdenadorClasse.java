package LinkedListsStacksAndQueues;

public class OrdenadorClasse<T extends Comparable>{

    public QueueADT<T> ordenar2Queues(QueueADT<T> queue1, QueueADT<T> queue2){
        QueueADT<T> resultado = new CircularArrayQueue<>();
        while (!queue1.isEmpty() && !queue2.isEmpty()){
            if(queue1.first().compareTo(queue2.first()) <= 0){
                resultado.enqueue(queue1.dequeue());
            }else {
                resultado.enqueue(queue2.dequeue());
            }
        }
        QueueADT<T> notEmptyQueue;

        if(!queue1.isEmpty())notEmptyQueue = queue1;
        else //if(!queue2.isEmpty())
            notEmptyQueue =  queue2;
        //else return resultado;

        while (!notEmptyQueue.isEmpty())resultado.enqueue(notEmptyQueue.dequeue());
        return resultado;
    }
    /*
     Dadas  N strings, criar N Queues cada    uma    contendo    uma    das    strings.
     De seguida criar uma Queue das N Queues.
     Repetidamente     aplicar     uma     operação de junção ordenada às primeiras duas Queues e  re-­‐inserir
     a    nova    Queue no    final.    Repetir    até    que    a    Queue contenha    apenas    uma Queue.
        */

    public QueueADT<QueueADT<T>> Ex5(T[] comparables) {
        QueueADT<QueueADT<T>> queueOfqueues = new CircularArrayQueue<>();
        for (int i = 0; i < comparables.length; i++) {
            queueOfqueues.enqueue(new CircularArrayQueue<>(comparables[i]));
        }
        QueueADT<T> c;
        while (queueOfqueues.size() > 1) {
            //OrdenadorClasse<T> ordenador = new OrdenadorClasse<>();

            c = ordenar2Queues(queueOfqueues.dequeue(), queueOfqueues.dequeue());
            queueOfqueues.enqueue(c);
        }
        return queueOfqueues;
    }

    public OrdenadorClasse() {
    }
}