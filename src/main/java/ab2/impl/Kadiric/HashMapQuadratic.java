package ab2.impl.Kadiric;

import ab2.AbstractHashMap;

public class HashMapQuadratic extends AbstractHashMap {

    int brojac;

    public HashMapQuadratic(int size)
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

    @Override
    public boolean put(int key, String value)
    {
        int adresa = hashFunction(key);
        Integer visited_add[] = new Integer[capacity()];
        while(!isEmpty(adresa))
        {
            visited_add[adresa]=1;
            if(getKey(adresa)==key)
                return false;
            adresa=(adresa*adresa)%capacity();
            if(visited_add[adresa]!=null)
                return false;
        }
        brojac++;
        setKeyAndValue(adresa,key,value);
        return true;
    }

    @Override
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
            adresa=(adresa*adresa)%capacity();
            if(adresa==poc_adresa)
                return null;
        }
    }

    @Override
    public int count()
    {
        return brojac;
    }

}
