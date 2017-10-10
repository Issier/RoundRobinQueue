/**
 * Created by Ryan on 4/15/2017.
 */
public class PCBNode {
    private PCB element;
    private PCBNode next;

    public PCBNode(PCB element, PCBNode next){
        this.element = element;
        this.next = next;
    }

    public PCBNode(PCB element){
        this(element, null);
    }

    public PCB getElement() {
        return element;
    }

    public void setElement(PCB element) {
        this.element = element;
    }

    public PCBNode getNext() {
        return next;
    }

    public void setNext(PCBNode next) {
        this.next = next;
    }
}
