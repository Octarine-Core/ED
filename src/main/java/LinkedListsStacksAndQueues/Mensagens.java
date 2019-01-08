package LinkedListsStacksAndQueues;

public class Mensagens {


    public String encode(String message, QueueADT<Integer> cifra){
        String codedMsg = "";

        for(int i = 0; i < message.length(); i++){
            int num = cifra.dequeue();
            codedMsg = codedMsg +  (char)((int)message.charAt(i) + num);
            cifra.enqueue(num);
        }
        return codedMsg;
    }

    public String decode(String message, QueueADT<Integer> cifra){
        String decodedMsg = "";

        for(int i = 0; i< message.length(); i++){
            int num = cifra.dequeue();
            decodedMsg = decodedMsg + (char) ((int)message.charAt(i) - num);
            cifra.enqueue(num);
        }

        return decodedMsg;
    }

    public void Mensagens(){

    }
}
