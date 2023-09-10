package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    private static final String INPUT = """
            noop
            noop
            noop
            addx 5
            addx 1
            addx 4
            addx 1
            noop
            addx 4
            noop
            addx 1
            addx 4
            addx 8
            addx -7
            addx 3
            addx 1
            noop
            addx 4
            addx 2
            addx 5
            addx -1
            noop
            addx -37
            noop
            noop
            addx 3
            addx 2
            addx 13
            addx 12
            addx -15
            addx -2
            addx 2
            addx -11
            addx 18
            addx 2
            addx -15
            addx 16
            addx 5
            addx 2
            addx 5
            noop
            noop
            noop
            addx 3
            addx -2
            addx -38
            noop
            addx 3
            addx 4
            noop
            noop
            noop
            noop
            noop
            addx 5
            addx 5
            noop
            noop
            addx 21
            addx -17
            addx 6
            noop
            noop
            noop
            noop
            addx 5
            noop
            noop
            noop
            noop
            noop
            addx 3
            addx 5
            addx -38
            noop
            noop
            addx 5
            addx -2
            addx 1
            addx 7
            noop
            addx 22
            addx -18
            addx -11
            addx 27
            addx -13
            addx 2
            addx 5
            addx -8
            addx 9
            addx 2
            noop
            addx 7
            noop
            addx 1
            noop
            addx -38
            noop
            addx 2
            addx 5
            addx -3
            noop
            addx 8
            addx 11
            addx -6
            noop
            addx 24
            addx -31
            addx 10
            addx 2
            addx 5
            addx 3
            noop
            addx 2
            addx -29
            addx 21
            addx 11
            addx 5
            addx -39
            addx 4
            addx -2
            addx 2
            addx 7
            noop
            addx -1
            addx 2
            noop
            addx 4
            noop
            addx 1
            addx 2
            addx 5
            addx 2
            noop
            noop
            addx -6
            addx 9
            addx -18
            addx 25
            addx 3
            noop
            addx -17
            noop
            """;

    public static void main(String[] args) {
        String[] inputs = INPUT.split(System.lineSeparator());

        List<String[]> draw = new ArrayList<>();

        int cycle = 0;
        int sum = 0;
        int x = 1;
        List<Integer> interestedCycle = Arrays.asList(20,60,100,140,180,220);

        String[] row  = new String[40];
        int ptr = 0;

        for (String in : inputs) {
            Instruction instruction = Instruction.parse(in);
            if (instruction.command.equals(Instruction.Command.ADDX)) {
                // ADDX
                for (int i = 0; i < 2; i++) {
                    // Start
                    cycle = cycle + 1;

                    // Middle
                    if (interestedCycle.contains(cycle)) {
                        int shouldAdd = x * cycle;
                        sum = sum + shouldAdd;
                    }
                    if (ptr == 40) {
                        draw.add(row);
                        row = new String[40];
                        ptr = 0;
                    }
                    if (shouldDrawPixel(x, ptr)) {
                        row[ptr] = "#";
                    } else {
                        row[ptr] = ".";
                    }
                    ptr++;

                    // End
                    if (i == 1) {
                        x = x + instruction.value;
                    }
                }
            } else {
                // NOOP
                // Start
                cycle = cycle + 1;

                // Middle
                if (interestedCycle.contains(cycle)) {
                    int shouldAdd = x * cycle;
                    sum = sum + shouldAdd;
                }
                if (ptr == 40) {
                    draw.add(row);
                    row = new String[40];
                    ptr = 0;
                }
                if (shouldDrawPixel(x, ptr)) {
                    row[ptr] = "#";
                } else {
                    row[ptr] = ".";
                }
                ptr++;

                // End
            }
        }
        draw.add(row);

        System.out.println("Answer part 1 : " + sum);

        System.out.println("Answer part 2 :");
        for (String[] rows : draw) {
            System.out.println();
            for (String pixel : rows) {
                System.out.print(pixel);
            }
            System.out.print("\n");
        }

        int k = 0;
    }

    record Instruction(Command command, Integer value) {
        enum Command {
            ADDX,
            NOOP
        }

        static Instruction parse(String in) {
            String[] instruction = in.split(" ");
            if (instruction.length == 1) {
                return new Instruction(Command.NOOP, 0);
            } else {
                return new Instruction(Command.ADDX, Integer.parseInt(instruction[1]));
            }
        }
    }

    private static boolean shouldDrawPixel(int x, int crt) {
        return x == crt || x - 1 == crt || x + 1 == crt;
    }

}