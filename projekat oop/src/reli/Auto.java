
package reli;


public class Auto extends Template{
    private static int id=0;
    private int id_auto;    
    private String model;
    private String marka;
    private int snaga;
    private int godiste;

    public Auto(String model, String marka, int snaga, int godiste) {
        this.id_auto = super.dodajIndeks(id++);
        this.model = model;
        this.marka = marka;
        this.snaga = snaga;
        this.godiste = godiste;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getMarka() {
        return marka;
    }

    public void setMarka(String marka) {
        this.marka = marka;
    }

    public int getSnaga() {
        return snaga;
    }

    public void setSnaga(int snaga) {
        this.snaga = snaga;
    }

    public int getGodiste() {
        return godiste;
    }

    public void setGodiste(int godiste) {
        this.godiste = godiste;
    }

    public int getId_auto() {
        return id_auto;
    }

    public void setId_auto(int id_auto) {
        this.id_auto = id_auto;
    }

    
    
    @Override
    public String toString() {
        return model + " " + marka + " " + snaga + "ks/" + (int)(snaga*0.74) + "kw "+ godiste+".";
    }
    
     
}
