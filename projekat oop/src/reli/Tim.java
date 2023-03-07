
package reli;

import java.util.ArrayList;

public class Tim extends Template implements Korisno{
    private static int id=0;
    private int id_tima;
    private Auto auto;
    private ArrayList<Trkac> clanovi;
    private int br_pobeda;
    private int br_poena;

    public Tim(String naziv){
        this.id_tima = super.dodajIndeks(id++);
        super.naziv = prvoSlovo(naziv);
        this.clanovi = new ArrayList<>(); 
        this.auto = null;
        this.br_pobeda=0;
        this.br_poena=0;        
    }
    public Tim(int id_tima, String naziv, int br_pobeda, int br_poena){
        this.id_tima = id_tima;
        super.naziv = naziv;
        this.clanovi = new ArrayList<>(); 
        this.auto = null;
        this.br_pobeda=br_pobeda;
        this.br_poena=br_poena;
    }

    public Tim() {        
    }
    

    public int getBr_pobeda() {
        return br_pobeda;
    }

    public void setBr_pobeda(int br_pobeda) {
        this.br_pobeda = br_pobeda;
    }

    public int getBr_poena() {
        return br_poena;
    }

    public void setBr_poena(int br_poena) {
        this.br_poena = br_poena;
    }

    public int getId_tima() {
        return id_tima;
    }

    public Auto getAuto() {
        return auto;
    }

    public void setAuto(Auto auto) {
        this.auto = auto;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }
        
    public void dodajPobedu(){
        this.br_pobeda+=1;
    }
    public void dodajPoene(int poeni){
        this.br_poena+=poeni;
    }

    public ArrayList<Trkac> getClanovi() {
        return clanovi;
    }

    public void setClanovi(ArrayList<Trkac> clanovi) {
        this.clanovi = clanovi;
    }
    
    public void azurirajID(int novi_id){
        id=novi_id;
    }
    
    
    public void dodajTrkaca(Trkac clan)
    {
        boolean postoji= false;
        
        for(Trkac t:this.clanovi)        
            if (t.getNaziv().equalsIgnoreCase(clan.getNaziv()) || clan.getTim()!=null)
                postoji=true;
        
        if (!postoji)
        {
            this.clanovi.add(clan);
            clan.setTim(this);
        }
        else
            System.out.println(clan.getNaziv()+" se ne moze ubaciti!");
    }

    @Override
    public String toString() {
        String format=String.format("%-2d %-15s %-6s %-9d %-8d ",id_tima, super.naziv, this.auto.getMarka(), this.getBr_pobeda(), this.getBr_poena());
        for(Trkac t:clanovi)
            format+= t.sazetString();
        return format;
    }

    @Override
    public String prvoSlovo(String naziv) {
        String ime;
        ime = naziv.substring(0,1).toUpperCase()+naziv.substring(1);
        return ime;
    }
}
