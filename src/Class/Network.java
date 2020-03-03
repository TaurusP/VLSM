/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Class;

import Class.SubNetwork;
import java.util.ArrayList;
import javax.swing.JOptionPane;
/**
 *
 * @author PJGP
 */
public class Network {
    
    private int Octects[];
    private int HostCount;
    private int MaxHostAllow;
    private String ClassMainAddress;
    private String ClassType;
    private ArrayList<SubNetwork> SubNetworksList;
    
    public Network (int MaxHostAllow){
        this.MaxHostAllow = MaxHostAllow;
        this.SelectClassType();
        this.SubNetworksList = new ArrayList<SubNetwork>();
        
    }
    
    public void SelectClassType(){
        int HN = DefineSubNetwoks(this.MaxHostAllow);
        HN = HN - 2;
        System.out.println(HN);
        this.Octects = null;
        if ((HN >0)&&(HN<=255)){
          this.ClassType="C";
          this.ClassMainAddress = "192.168.1";
          this.Octects = new int[1];
        }
        if ((HN >=256)&&(HN<=65000)){
            this.ClassType="B";
            this.ClassMainAddress = "172.16";
            this.Octects = new int[2];
        }
        if ((HN >=65001)&&(HN<=16500000)){
            this.ClassType="A";
            this.ClassMainAddress = "10";
            this.Octects = new int[3];
        }
    }
    
    public int DefineSubNetwoks(int NHost){
        Boolean flag = true;
        int pow;
        int auxpow = 0;
        while(flag){
            pow = (int) Math.pow(2,auxpow);
            if(NHost<=pow-2){
                return pow;
            }else{
                auxpow++;
            }
        }
        return NHost;
    }
    
    public boolean AddSubNetwork(int NHost,String Type){
        int sum = HostCount + NHost;
        if(MaxHostAllow >= sum){
            System.out.println("CREANDO OBJETO SUBNETWORK");
            SubNetwork temp = new SubNetwork(Type,this.ClassMainAddress);
            System.out.println("EMPEZANDO CALCULO DE SUBNETWORK");
            Octects = temp.CalculateSubNetwork(DefineSubNetwoks(NHost), Octects);
            System.out.println("CALCULO DE SUBNETWORK EXITOSO");
            this.SubNetworksList.add(temp);
            this.HostCount+=NHost;
            return true;
        }else{
            JOptionPane.showMessageDialog(null, "Capacidad de la red superada");
            return false;
        }
        
    }
    
    public String getClassType(){
        return this.ClassType;
    }
    
    public String getClassMainAddress(){
        return this.ClassMainAddress;
    }
    public ArrayList<SubNetwork> getSubNetworskList(){
        return this.SubNetworksList;
    }
    
    public SubNetwork getLastSubNetwork(){
        return this.SubNetworksList.get(this.SubNetworksList.size()-1);
    }
    
}
