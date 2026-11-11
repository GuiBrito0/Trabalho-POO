package Entidades;
public class Carro extends Veiculo {
    private String categoria;

    public Carro(String placa, String modelo, int ano, double valorDiaria, Motor motor, String categoria) {
        super(placa, modelo, ano, valorDiaria, motor);
        this.categoria = categoria;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    @Override
    public Double calcularDiaria() {
    if (categoria == null) return getValorDiaria();
    switch (categoria.toUpperCase()) {
        case "SUV":
            return getValorDiaria() + 40.0;
        case "PICAPE":
            return getValorDiaria() + 50.0;
        case "SEDAN":
            return getValorDiaria() + 20.0;
        default:
            return getValorDiaria();
    }
}

    @Override
    public String getTipo() {
        return "Carro";
    }
}
