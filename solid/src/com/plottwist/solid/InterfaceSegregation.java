package com.plottwist.solid;
/**
 * Basically a recommendation of how to split interfaces
 * into smaller interfaces
 * -> Never put into the interface more than what the
 * client is expected to implement
 * <p>
 * YAGNI ==> You Ain't Going to Need It
 */
class InterfaceSegregation {
}

class Document {
}
//Take an interface(ie Machine) which allows clients to perform ops on Document
interface Machine {
    void print(Document doc);

    void fax(Document doc);

    void scan(Document doc);
}
/**
 * A client wants to create a multifunction printer,so it implements Machine.
 * This can get really bad if client just wants OldFashionedPrinter
 */
class MultiFunctionPrinter implements Machine {
    @Override
    public void print(Document doc) {
        //
    }

    @Override
    public void fax(Document doc) {
        //
    }

    @Override
    public void scan(Document doc) {
        //
    }
}
/**
 * This is where the problem begins -> client has to implement ALL of the methods defined in the Machine interface
 * What should client do on fax and scanner methods (which he don't need?)
 * If left empty, developers CAN TRY TO USE THEM -> this is a problem, cause they don't do shit
 */
class OldFashionedPrinter implements Machine {
    @Override
    public void print(Document doc) {

    }

    @Override
    public void fax(Document doc) {
        //Adding this will not work cause you have to change signature....
        //Maybe you do not own source code to modify it
        //throw new Exception();
    }

    @Override
    public void scan(Document doc) {

    }
}
/**
 * A solution is to split the Machine interface
 */
interface Printer {
    void print(Document doc);
}

interface Scanner {
    void scan(Document doc);
}

interface Faxer {
    void fax(Document doc);
}

class JustAPrinter implements Printer {
    @Override
    public void print(Document doc) {
    }
}

class ScanAndFax implements Scanner, Faxer {
    @Override
    public void scan(Document doc) {

    }

    @Override
    public void fax(Document doc) {

    }
}
/**
 * Another solution -> Create an interface that extends from to separate interfaces
 */
interface Photocopier extends Printer, Scanner{
}

class PhotocopierImpl implements Photocopier{
    @Override
    public void print(Document doc) {

    }

    @Override
    public void scan(Document doc) {

    }
}