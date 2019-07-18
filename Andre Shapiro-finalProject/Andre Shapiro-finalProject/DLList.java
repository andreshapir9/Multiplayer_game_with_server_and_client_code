import java.io.Serializable;
public class DLList<E> implements Serializable{
	private Node<E> head,tail;
	private int size;
	public DLList(){
		head=new Node<E>(null);
		tail=new Node<E>(null);
		head.setNext(tail);
		head.setPrev(null);
		tail.setNext(null);
		tail.setPrev(head);
		size=0;
	}
	public Node<E> getNode(int location){
		Node<E> current=head.next();
		for(int i=0;i<location;i++){
			if(current!=null){
				current=current.next();
			}
			else{
				return null;
			}
		}
		return current;
	}
	public void add(E data){
		size++;
		Node<E> node=new Node<E>(data);
		node.setPrev(tail.prev());
		node.setNext(tail);
		tail.prev().setNext(node);
		tail.setPrev(node);
	}
	public void add(int location,E data){
		size++;
		Node<E> node=new Node<E>(data);
		Node<E> current=getNode(location);
		node.setPrev(current.prev());
		node.setNext(current);
		current.prev().setNext(node);
		current.setPrev(node);
	}
	public E get(int location){
		return getNode(location).get();
	}
	public int size(){
		return size;
	}
	public String toString(){
		String string="";
		int location=1;
		Node<E> current=head.next();
		while(current.next().next()!=null){
			string+=location+". "+current.get()+"\n";
			current=current.next();
			location++;
		}
		return string+location+". "+current.get();
	}
	public void remove(int location){
		if(location<size){
			Node<E> node=getNode(location);
			node.prev().setNext(node.next());
			node.next().setPrev(node.prev());
		}
	}
	public void remove(E data){
		Node<E> current=head.next();
		while(current.get()!=null){
			if(current.get().equals(data)){
				current.prev().setNext(current.next());
				current.next().setPrev(current.prev());
				size--;
			}
			current=current.next();
		}
	}
	public void set(int location,E data){
		getNode(location).set(data);
	}
	public boolean contains(E data){
		Node<E> current=head.next();
		while(current.get()!=null){
			if(data.equals(current.get())){
				return true;
			}
			current=current.next();
		}
		return false;
	}
}