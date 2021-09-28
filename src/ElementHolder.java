public class ElementHolder {

    private ElementMolecule em;
    private Element e;

    public ElementHolder(ElementMolecule em){
        this.em = em;
    }

    public ElementHolder(Element e){
        this.e = e;
    }

    public int getNum(){
        return e == null ? em.getNum() : e.getNum();
    }

    public boolean isElement(){
        return e != null;
    }

    public String getSymbol(){
        return e == null ? em.getSymbol() : e.getSymbol();
    }

    public String toString(){
        return e == null ? em.toString() : e.toString();
    }

}
