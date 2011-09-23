/*
 * Copyright (c) 2007-2011 by The Broad Institute of MIT and Harvard.  All Rights Reserved.
 *
 * This software is licensed under the terms of the GNU Lesser General Public License (LGPL),
 * Version 2.1 which is available at http://www.opensource.org/licenses/lgpl-2.1.php.
 *
 * THE SOFTWARE IS PROVIDED "AS IS." THE BROAD AND MIT MAKE NO REPRESENTATIONS OR
 * WARRANTES OF ANY KIND CONCERNING THE SOFTWARE, EXPRESS OR IMPLIED, INCLUDING,
 * WITHOUT LIMITATION, WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE, NONINFRINGEMENT, OR THE ABSENCE OF LATENT OR OTHER DEFECTS, WHETHER
 * OR NOT DISCOVERABLE.  IN NO EVENT SHALL THE BROAD OR MIT, OR THEIR RESPECTIVE
 * TRUSTEES, DIRECTORS, OFFICERS, EMPLOYEES, AND AFFILIATES BE LIABLE FOR ANY DAMAGES
 * OF ANY KIND, INCLUDING, WITHOUT LIMITATION, INCIDENTAL OR CONSEQUENTIAL DAMAGES,
 * ECONOMIC DAMAGES OR INJURY TO PROPERTY AND LOST PROFITS, REGARDLESS OF WHETHER
 * THE BROAD OR MIT SHALL BE ADVISED, SHALL HAVE OTHER REASON TO KNOW, OR IN FACT
 * SHALL KNOW OF THE POSSIBILITY OF THE FOREGOING.
 */

package org.broad.igv.hic.tools;

import org.broad.igv.hic.data.Chromosome;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * @author Jim Robinson
 * @date 9/16/11
 */
public class HiCTools {


    public static void main(String[] args) throws IOException {

        if (args.length < 3) {
            System.out.println("Usage: hictools <command> <inputFile> <outputFile>");
        }

        if (args[0].equals("sort")) {
            AlignmentsSorter.sort(args[1], args[2], null);
        } else if (args[0].equals("pre")) {
            String genomeId = args[3];
            if (genomeId.equals("hg18")) {
                chromosomes = hg18Chromosomes;
            } else {
                chromosomes = dmelChromosomes;
            }

            long genomeLength = 0;
            for (Chromosome c : chromosomes) {
                if (c != null)
                    genomeLength += c.getSize();
            }
            chromosomes[0] = new Chromosome(0, "All", (int) (genomeLength / 1000));

            chromosomeOrdinals = new Hashtable();
            for (int i = 0; i < chromosomes.length; i++) {
                chromosomeOrdinals.put(chromosomes[i].getName(), i);
            }

            String[] tokens = args[1].split(",");
            List<File> files = new ArrayList(tokens.length);
            for (String f : tokens) {
                files.add(new File(f));
            }

            (new Preprocessor(new File(args[2]))).preprocess(files, genomeId);
        }
    }

    public static Chromosome[] chromosomes;
    public static Map<String, Integer> chromosomeOrdinals;

    // Hardcoded chromosomes for dMel r4.2.1
    public final static Chromosome[] dmelChromosomes = new Chromosome[]{
            null,                                 // Placeholder for whole genome
            new Chromosome(1, "2L", 22407834),
            new Chromosome(2, "2R", 20766785),
            new Chromosome(3, "3L", 23771897),
            new Chromosome(4, "3R", 27905053),
            new Chromosome(5, "4", 1281640),
            new Chromosome(6, "X", 22224390)
    };

    public final static Chromosome[] hg18Chromosomes = new Chromosome[]{
            null,                               // Placeholder for whole genome
            new Chromosome(1, "1", 247249719),
            new Chromosome(2, "2", 242951149),
            new Chromosome(3, "3", 199501827),
            new Chromosome(4, "4", 191273063),
            new Chromosome(5, "5", 180857866),
            new Chromosome(6, "6", 170899992),
            new Chromosome(7, "7", 158821424),
            new Chromosome(8, "8", 146274826),
            new Chromosome(9, "9", 140273252),
            new Chromosome(10, "10", 135374737),
            new Chromosome(11, "11", 134452384),
            new Chromosome(12, "12", 132349534),
            new Chromosome(13, "13", 114142980),
            new Chromosome(14, "14", 106368585),
            new Chromosome(15, "15", 100338915),
            new Chromosome(16, "16", 88827254),
            new Chromosome(17, "17", 78774742),
            new Chromosome(18, "18", 76117153),
            new Chromosome(19, "19", 63811651),
            new Chromosome(20, "20", 62435964),
            new Chromosome(21, "21", 46944323),
            new Chromosome(22, "22", 49691432),
            new Chromosome(23, "23", 154913754),
            new Chromosome(24, "24", 57772954),
            new Chromosome(25, "0", 16571),
    };


}