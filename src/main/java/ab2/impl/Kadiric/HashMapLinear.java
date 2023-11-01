package ab2.impl.Kadiric;

import ab2.AbstractHashMap;

public class HashMapLinear extends AbstractHashMap
{
    int brojac;

    public HashMapLinear(int size)
    {
        brojac=0;
        initTable(size);
    }

    private int hashFunction(int key)
    {
        int adresa = key%capacity();
        if(adresa<0)
            adresa+=capacity();
        return adresa;
    }

    public boolean put(int key, String value)
    {
        int adresa = hashFunction(key);
        int poc_adresa = adresa;
        while(!isEmpty(adresa))
        {
            if(getKey(adresa)==key)
                return false;
            adresa=(adresa+1)%capacity();
            if(adresa==poc_adresa)
                return false;
        }
        brojac++;
        setKeyAndValue(adresa,key,value);
        return true;
    }


    public String get(int key)
    {
        int adresa = hashFunction(key);
        int poc_adresa = adresa;
        while(true)
        {
            if(isEmpty(adresa))
                return null;
            int kljuc = getKey(adresa);
            if(kljuc==key)
                return getValue(adresa);
            adresa=(adresa+1)%capacity();
            if(adresa==poc_adresa)
                return null;
        }
    }


    public int count()
    {
        return brojac;
    }

}