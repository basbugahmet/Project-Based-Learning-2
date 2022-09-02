import java.lang.reflect.Array;
import java.util.ArrayList;

public class Queue {
    private int rear, front;
    private Object[] elements;
    private int capacityOfQueue;
    Queue(int capacity){
        elements = new Object[capacity];
        capacityOfQueue = elements.length;
        rear = -1;
        front = 0;
    }
    void enqueue(Object data) {
        if(isFull())
            System.out.println("Queue overflow");
        else {
            rear++;
            elements[rear] = data;
        }
    }
    Object dequeue() {
        if(isEmpty())
        {
            System.out.println("Queue is empty");
            return null;
        }
        else {
            Object retData = elements[front];
            elements[front] = null;
            front++;
            return retData;
        }
    }
    Object peek() {
        if(isEmpty()) {
            System.out.println("Queue is empty");
            return null;
        }
        else {
            return elements[front];
        }
    }
    boolean isEmpty() {
        return rear<front;
    }
    boolean isFull() {
        return (rear+1 == elements.length);
    }
    int size() {
        return rear-front+1;
    }

    public int getCapacityOfQueue() {
        return capacityOfQueue;
    }

    public void setCapacityOfQueue(int capacityOfQueue) {
        this.capacityOfQueue = capacityOfQueue;
    }

    public void getFirst15(){
        int i = 0;
        int n = 0;

        while(n<15){
            if(elements[i] != null){
                System.out.print(((Treasure)elements[i]).value);
                n++;
            }
            i++;
        }

    }

    public void clearAll(){
        for(int i = 0; i<size();i++){
            dequeue();
        }
        rear = -1;
        front = 0;
    }

}
