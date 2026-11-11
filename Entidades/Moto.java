package Entidades;
public class Moto extends Veiculo {
    private int cilindradas;

    public Moto(String placa, String modelo, int ano, double valorDiaria, Motor motor, int cilindradas) {
        super(placa, modelo, ano, valorDiaria, motor);
        this.cilindradas = cilindradas;
    }

    public int getCilindradas() {
        return cilindradas;
    }

    public void setCilindradas(int cilindradas) {
        this.cilindradas = cilindradas;
    }
    
    @Override
    public Double calcularDiaria() {
        if (cilindradas > 500) {
            return getValorDiaria() * 1.3;
        }
        return getValorDiaria();
    }
    
    @Override
    public String getTipo() {
        return "Moto";
    }
}
