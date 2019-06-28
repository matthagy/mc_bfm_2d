package com.matthagy.mc_bfm_2d.simulation;

import com.matthagy.mc_bfm_2d.state.Configuration;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.Random;
import java.util.Scanner;

public class Simulator {

    public static void main(String[] args) throws Exception {
        final long cycleDuration = Long.parseLong(args[0]);
        final long cycles = Long.parseLong(args[1]);
        final long seed = Long.parseLong(args[2]);

        String inputLine = new Scanner(System.in).nextLine();
        Configuration configuration = Configuration.fromJson(
                (JSONObject) new JSONParser().parse(inputLine));

        long steps = 0;
        Propagator propagator = new Propagator(configuration, new Random(seed));
        for (int cycleIndex=0; cycleIndex<cycles; cycleIndex++ ) {
            for (int stepIndex=0; stepIndex<cycleDuration; stepIndex++) {
                propagator.propagate();
                steps ++;
            }
            JSONObject configJson = configuration.toJson();
            configJson.put("steps", steps);
            System.out.println(configJson.toJSONString());
        }


    }
}
