package com.example.serhio.substantiv.logic;

import com.example.serhio.substantiv.entities.Substantiv;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Serhio on 01.03.2018.
 */

public class SubstantivManager {
    private static Random random;
    private static List<Substantiv> substantivList;

    public SubstantivManager() {
        random = new Random();
        substantivList = new ArrayList<>();
        substantivList.add(new Substantiv("Mann", "Der"));
        substantivList.add(new Substantiv("Mutter", "Die"));
        substantivList.add(new Substantiv("Moment", "Der"));
        substantivList.add(new Substantiv("Schule", "Die"));
        substantivList.add(new Substantiv("Familie", "Die"));
        substantivList.add(new Substantiv("Essen", "Das"));

    }

    public Substantiv getNextSubstantiv() {
        int index = random.nextInt(substantivList.size());
        return substantivList.get(index);
    }

    public Substantiv getNext() {
        return null;
    }
}
