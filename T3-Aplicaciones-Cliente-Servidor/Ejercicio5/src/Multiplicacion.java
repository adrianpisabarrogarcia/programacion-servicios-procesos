public class Multiplicacion {

    private int multiplicando;
    private int multiplicando2;
    private double total;

    public Multiplicacion(int multiplicando, int multiplicando2) {
        this.multiplicando = multiplicando;
        this.multiplicando2 = multiplicando2;
    }

    public int getMultiplicando() {
        return multiplicando;
    }

    public void setMultiplicando(int multiplicando) {
        this.multiplicando = multiplicando;
    }

    public int getMultiplicando2() {
        return multiplicando2;
    }

    public void setMultiplicando2(int multiplicando2) {
        this.multiplicando2 = multiplicando2;
    }

    public double getTotal() {
        return multiplicando * multiplicando2;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String toString() {
        return multiplicando + " " + multiplicando2;
    }
}
