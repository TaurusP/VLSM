/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Class;
import java.util.ArrayList;
/**
 *
 * @author PJGP
 */
public class SubNetwork {
    protected ArrayList<String> AsignableAddress;
    protected String SubNetworkAddress;
    protected String Gateway;
    protected String Broadcast;
    private String Type;
    private String BaseAddress;
    protected String Mask;
    
    public SubNetwork(String Type, String BaseAddress){
        this.Type = Type;
        this.BaseAddress = BaseAddress;
        this.AsignableAddress = new ArrayList<String>();
    }
    
    public int[] CalculateSubNetwork(int NHost,int[]Octects){
        System.out.println("CALCULANDO MASCARA");
        this.SubNetmask(NHost);
        System.out.println("CALCULO DE MASCARA EXITOSO");
        System.out.println("CALCULANDO DIRECCIONES");
        return this.SubNetAddress(NHost, Octects);
    }
    
    public int[] ControlOctects (int[] Octects){
        for (int i = Octects.length-1; i >= 0; i--) {
            if(Octects[i]==255)
                Octects[i] = 0;
            else{
                Octects[i]+=1;
                return Octects;
            }
       }
        return Octects;
    }
    
    public String ConcatAddress(int[]Octects){
        String str_line = BaseAddress;
        for (int i = 0; i < Octects.length; i++) {
            str_line = str_line+"."+Octects[i];
        }
        return str_line;
    }
    
    public int[] SubNetAddress(int NHost,int[]Octects){
        //Direccion de red
        this.SubNetworkAddress=ConcatAddress(Octects);
        Octects = ControlOctects(Octects);
        
        if(this.Type.equals("Router")){
            for (int i = 0; i < NHost-2; i++) {
                //Primera Asignable
                this.AsignableAddress.add(ConcatAddress(Octects));
                Octects = ControlOctects(Octects);    
            }
            //Gateway
            this.Gateway="N/A";
        }else{
            for (int i = 0; i < NHost-3; i++) {
                this.AsignableAddress.add(ConcatAddress(Octects));
                Octects = ControlOctects(Octects);           
            }
            //Gateway
            this.Gateway=ConcatAddress(Octects);
            Octects = ControlOctects(Octects);
        }
        //Broadcast
        this.Broadcast=ConcatAddress(Octects);
        Octects = ControlOctects(Octects);
        return Octects;
    }
    
    public void SubNetmask(int NHost){
        int OctectBinarySubMask[][]= new int [4][8];
        int auxpow=0;
        int SubNetMaskLen;
        while(NHost != (int) Math.pow(2,auxpow)){
            auxpow++;
        }
        SubNetMaskLen = 32-auxpow;
        int aux = 0;
        System.out.println("COMENZANDO DE MATRIZ BINARIA");
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 8; j++) {
                if(aux<SubNetMaskLen)
                    OctectBinarySubMask[i][j]=1;
                else
                    OctectBinarySubMask[i][j]=0;
                
                aux++;
            }
        }
        System.out.println("LLENADO DE MATRIZ BINARIA COMPLETADO");
        int Oct []= new int[4];
        int powoct;
        int algo;
        for (int i = 0; i < 4; i++) {
            algo=7;
            powoct = 0;
            for (int j = 0; j < 8; j++) {
                if(OctectBinarySubMask[i][j]==1)
                    powoct += (int)Math.pow(2, algo);
                else
                    break;
                algo--;
            }
            Oct[i]=powoct;
        }
        System.out.println("FINAL METODO ANTES DE ASIGNACIÓN");
        this.Mask = Oct[0]+"."+Oct[1]+"."+Oct[2]+"."+Oct[3];
    }

    public ArrayList<String> getAsignableAddress() {
        return AsignableAddress;
    }

    public String getSubNetworkAddress() {
        return SubNetworkAddress;
    }

    public String getGateway() {
        return Gateway;
    }

    public String getBroadcast() {
        return Broadcast;
    }

    public String getSubNetMask() {
        return Mask;
    }
    
    public String getFirstAddress(){
        return this.AsignableAddress.get(0);
    }
    
    public String getLastAddress(){
        return this.AsignableAddress.get(this.AsignableAddress.size()-1);
    }
}
