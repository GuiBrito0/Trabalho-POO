package Entidades;
public class Motor {
    private int potencia;
    private String combustivel;
    public Motor(int potencia, String combustivel) {
        this.potencia = potencia;
        this.combustivel = combustivel;
    }
    public int getPotencia() {
        return potencia;
    }
    public void setPotencia(int potencia) {
        this.potencia = potencia;
    }
    public String getCombustivel() {
        return combustivel;
    }
    public void setCombustivel(String combustivel) {
        this.combustivel = combustivel;
    }
}
