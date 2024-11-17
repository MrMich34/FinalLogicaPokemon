package com.org;

import org.springframework.boot.autoconfigure.AbstractDependsOnBeanFactoryPostProcessor;
import utils.ManejadorArchivos;

import java.util.List;
import java.util.Random;

import static java.lang.Math.round;

public class Pelea {
    private Pokemon pokemon1;
    private Pokemon pokemon2;
    private static final ManejadorArchivos ma = new ManejadorArchivos();


    public Pelea(){

    }

    public Pelea(Pelea p){
        pokemon1 = new Pokemon(p.getPokemon1());
        pokemon2 = new Pokemon(p.getPokemon2());
        iniciarPelea();
    }

    public String iniciarPelea() {

        String ganador = "";
        int empieza = quienEmpieza();
        while(pokemon1.getEstadisticas().getHp() > 0 && pokemon2.getEstadisticas().getHp() > 0){
            if(empieza == 1){
                System.out.println("Empieza " + pokemon1.getNombre());
                double daño = ataque(pokemon1,pokemon2);
                System.out.println("El ataque de " + pokemon1.getNombre() + "se lo da a " + pokemon2.getNombre());
                System.out.println("El ataque inflinge: " + daño);
                System.out.println("Empieza la salud de " + pokemon2.getNombre() + " en " + pokemon2.getEstadisticas().getHp());
                pokemon2.cambiarSalud(daño);
                System.out.println("Termina la salud de " + pokemon2.getNombre() + " en " + pokemon2.getEstadisticas().getHp());
                if(pokemon2.getEstadisticas().getHp() <= 0){
                    System.out.println("Perdio " + pokemon2.getNombre());
                    ganador= pokemon1.getNombre();
                    break;
                }
                System.out.println("El ataque de " + pokemon2.getNombre() + "se lo da a " + pokemon1.getNombre());
                System.out.println("Empieza la salud de " + pokemon1.getNombre() + " en " + pokemon1.getEstadisticas().getHp());
                daño = ataque(pokemon2,pokemon1);
                System.out.println("El ataque inflinge: " + daño);
                System.out.println("Termina la salud de " + pokemon1.getNombre() + " en " + pokemon1.getEstadisticas().getHp());
                pokemon1.cambiarSalud(daño);
                if(pokemon1.getEstadisticas().getHp() <= 0){
                    System.out.println("Perdio " + pokemon1.getNombre());
                    ganador = pokemon2.getNombre();
                    break;
                }
            }else{
                System.out.println("Empieza " + pokemon2.getNombre());
                double daño = ataque(pokemon2,pokemon1);
                pokemon1.cambiarSalud(daño);
                if(pokemon1.getEstadisticas().getHp() <= 0){
                    System.out.println("Perdio el 1");
                    ganador = pokemon2.getNombre();
                    break;

                }
                daño = ataque(pokemon1,pokemon2);
                pokemon2.cambiarSalud(daño);
                if(pokemon2.getEstadisticas().getHp() <= 0){
                    ganador = pokemon1.getNombre();
                    System.out.println("Perdio el 2");
                    break;
                }
            }
        }
        return ganador;

    }

    public double ataque(Pokemon p1, Pokemon p2){

        int a = numeroDeMovimientoAUsar(p1);
        double efectividad = ma.obtenerEfectividad(p1.getMovimientos().get(a).getTipo(),p2.getEstadisticas().getTipo());
        double bonificacion = bonificacion(p1, a);
        Random random = new Random();
        double cantidadAtaque = cantidadAtaque(p1,a);
        double poder = poder(p1,a);
        int var = random.nextInt(85,100);
        double cantidadDefensa = cantidadDefensa(p1,p2,a);
        System.out.println("0.01 * "  + bonificacion + " * " + efectividad + " * " + var + " * " + ((0.2*50+1)) + " * " + cantidadAtaque + " * " + poder + " / " + (25*cantidadDefensa) + " * 2");
        double daño = 0.01*bonificacion*efectividad*var*(((0.2*50+1)*cantidadAtaque*poder)/(25*cantidadDefensa))+2;
        System.out.println(p1.getMovimientos().get(a));
        System.out.println(daño);
        return round(daño);
    }

    private double cantidadDefensa(Pokemon p1,Pokemon p2,int a) {
        if(p1.getMovimientos().get(a).getEstado().equalsIgnoreCase("physical")){
            return p2.getEstadisticas().getDefense();
        } else if (p1.getMovimientos().get(a).getEstado().equalsIgnoreCase("special")) {
            return p2.getEstadisticas().getSpecialDefense();
        }else{
            return 0.04;
        }
    }

    public double cantidadAtaque(Pokemon p1, int a){
        System.out.println("Ataque de tipo " + p1.getMovimientos().get(a).getEstado());
        if(p1.getMovimientos().get(a).getTipo().equalsIgnoreCase("physical")){
            return p1.getEstadisticas().getAttack();
        } else if (p1.getMovimientos().get(a).getEstado().equalsIgnoreCase("special")) {
            return p1.getEstadisticas().getSpecialAttack();
        }else{
            return 0.0;
        }
    }

    public double poder(Pokemon p1, int a){
        if(p1.getMovimientos().get(a).getPower() >-1){
            return p1.getMovimientos().get(a).getPower();
        }else{
            return 0;
        }
    }

    public double bonificacion(Pokemon p1, int a){
        if(p1.getEstadisticas().getTipo().equals(p1.getMovimientos().get(a).getTipo())){
            return 1.5;
        }else{
            return 1;
        }
    }

    public int numeroDeMovimientoAUsar(Pokemon p1) {
        Random random = new Random();
        int cant = p1.getMovimientos().size();
        return random.nextInt(cant);
    }

    public int quienEmpieza(){
        if(pokemon1.getEstadisticas().getSpeed() > pokemon2.getEstadisticas().getSpeed()){
            return 1;
        } else if (pokemon1.getEstadisticas().getSpeed() < pokemon2.getEstadisticas().getSpeed()) {
            return 2;
        }
        Random random = new Random();
        return random.nextInt(2);
    }
    public Pokemon getPokemon1(){
        System.out.println(pokemon1);
        return pokemon1;
    }
    public Pokemon getPokemon2(){
        System.out.println(pokemon2);
        return pokemon2;
    }


    @Override
    public String toString() {
        return "Pelea{" +
                "pokemon1=" + pokemon1 +
                ", pokemon2=" + pokemon2 +
                '}';
    }


}
