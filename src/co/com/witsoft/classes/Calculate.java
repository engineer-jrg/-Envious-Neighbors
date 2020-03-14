/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.witsoft.classes;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JSpinner;

/**
 *
 * @author EQUIPO
 */
public class Calculate extends Thread{
    
    private JSpinner[] j_neighbors;
    private JSpinner number_days;
    private JLabel[] labels_days;
    private JLabel[] labels_previus_days;
    private JLabel[] labels_next_days;
    
    public void run(){
      this.calculateDyas();
    }

    public Calculate() {
    }
    
    public void setNeighbors(JSpinner[] j_neighbors) {
        this.j_neighbors = j_neighbors;
    }
    
    public void setNumberDays(JSpinner number_days){
        this.number_days = number_days;
    }
    
    public void setLabelsDays(JLabel[] labels_days){
        this.labels_days = labels_days;
    }
    
    public void setStatusPreviusDays(JLabel[] labels_previus_days){
        this.labels_previus_days = labels_previus_days;
    }
    
    public void setStatusNextDays(JLabel[] labels_next_days){
        this.labels_next_days = labels_next_days;
    }
    
    public void calculateDyas(){
        // Desabilitar componentes
        this.neighborsSteted(false);
        this.number_days.setEnabled(false);
        
        // crear areglo con los valores de los vecinos
        int[] neighbors = this.getValueNeighbors();
        System.out.println("\n Estado inicial (dia 0):");
        this.prinState(neighbors);
        // crear una varible temporal para los valores siguientes
        int[] next_neighbors = {0,0,0,0,0,0,0,0};
        // variable para el numero de dias
        int n_d = Integer.parseInt(this.number_days.getValue().toString());
        // ciclo para los dias
        for (int i = 0; i < n_d; i++) {
            // cargar estado previo a los labels
            this.setStatusLabelsDays(neighbors, this.labels_previus_days);
            // variables para el estado de los vecinos
            int previus_neighbour = 0;
            int next_neighbour = 0;
            // ciclo para los vecinos
            for(int k = 0; k < neighbors.length; k++){
                // evitar que asigne un valor siguiente fuera de rango
                if(k == (neighbors.length-1) ){
                    next_neighbour = 0;
                }else{
                    next_neighbour = neighbors[k+1];
                }
                // cambiar el estado si sus vecinos son iguales
                if(previus_neighbour == next_neighbour){
                    previus_neighbour = neighbors[k];
                    neighbors[k] = changeState(neighbors[k]);
                }else{
                    previus_neighbour = neighbors[k];
                }
            }
            System.out.println("\n Estado siguiente (dia "+(i+1)+"):");
            this.prinState(neighbors);
            this.setDays(i);
            this.setStatusLabelsDays(neighbors, this.labels_next_days);
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                System.err.println("error 2: "+e.getMessage());
            }
        }
        // Habilitar componentes
        this.neighborsSteted(true);
        this.number_days.setEnabled(true);
    }
    
    private void neighborsSteted(boolean state){
        for(JSpinner jSpinner: this.j_neighbors){
            jSpinner.setEnabled(state);
        }
    }
    
    private int[] getValueNeighbors(){
        try {
            int[] neighbors = new int[this.j_neighbors.length];
            for (int i = 0; i < neighbors.length; i++) {
                neighbors[i] = Integer.parseInt(this.j_neighbors[i].getValue().toString());
            }
            return neighbors;
        } catch (Exception e) {
            System.err.println("error 1: "+e.getMessage());
        }
        int[] neighbors = {};
        return neighbors;
    }
    
    private int changeState(int state){
        if(state == 1){
            return 0;
        }
        return 1;
    }
    
    private void prinState(int[] array){
        for(int item: array){ System.out.print("| "+item);}
        System.out.println("");
    }
    
    private void setDays(int day){
        this.labels_days[0].setText(""+(day));
        this.labels_days[1].setText(""+(day+1));
    }
    
    private void setStatusLabelsDays(int[] days, JLabel[] labels){
        for(int i=0;i<days.length;i++){
            labels[i].setText("  "+days[i]+"  ");
            if(days[i]==1){
                labels[i].setBackground(new Color(239,94,94));
            }else{
                labels[i].setBackground(new Color(75,233,75));
            }
        }
    }
}
