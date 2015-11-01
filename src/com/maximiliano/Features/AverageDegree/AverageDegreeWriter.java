package com.maximiliano.Features.AverageDegree;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Maximiliano on 10/31/2015.
 * This class prints the average degree stream from a {@link AverageDegreeCalculator} into a {@link FileWriter};
 */
public class AverageDegreeWriter implements AverageDegreeCalculator.AverageDegreeListener {
    private final FileWriter fileWriter2;

    public AverageDegreeWriter(FileWriter fileWriter2) {
        this.fileWriter2 = fileWriter2;
    }

    @Override
    public void update(double avgDegree) {
        try {
            fileWriter2.write(avgDegree + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
