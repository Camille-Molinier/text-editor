import Interfaces.CareTaker;
import Interfaces.Memento;
import Interfaces.Originator;
import implementations.Memento.MyOriginator;
import implementations.Memento.Snapshot;
import implementations.Memento.StackCareTaker;


public class tester {

  public static void main(String[] args) {
    CareTaker c = StackCareTaker.getInstance();
/*
    c.addMemento(new Snapshot("groink"));
    c.addMemento(new Snapshot("rhrdbb"));
    c.addMemento(new Snapshot("grodrbdbrdink"));
    c.addMemento(new Snapshot("sebvsrn"));
    c.addMemento(new Snapshot("qfqghtdrds"));
    c.getMemento();
    c.getMemento();

    c.addMemento(new Snapshot("egbb"));
    c.addMemento(new Snapshot("eshjjdjryyhhchh"));

    //System.out.println(c.getMementoList(tmp));*/
  }
}
