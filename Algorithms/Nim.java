/*
 * AI for Nim game where last coin taken wins.
 *
 * Time complexity: O(N) per move where N is the number of stacks.
 */

package codebook.algorithms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Nim {

  static BufferedReader br;
  static PrintWriter out;
  static StringTokenizer st;

  static int n, sum;
  static int[] a;

  public static void main (String[] args) throws IOException {
    br = new BufferedReader(new InputStreamReader(System.in));
    out = new PrintWriter(new OutputStreamWriter(System.out));
    //br = new BufferedReader(new FileReader("in.txt"));
    //out = new PrintWriter(new FileWriter("out.txt"));

    n = readInt();
    a = new int[n];

    for (int i = 0; i < n; i++)
      sum += a[i] = readInt();

    boolean computerTurn = getSum() != 0;

    while (sum > 0) {

      out.print("Current piles: ");
      for (int i = 0; i < n; i++)
        out.print(a[i] + " ");
      out.println();

      if (!computerTurn) {
        out.print("Enter the pile to take from: ");
        int pile = readInt();
        out.print("Enter the number of coins to take: ");
        int coins = readInt();

        a[pile] -= coins;
        sum -= coins;
      } else {
        int coins = getSum();
        int pile = -1;
        int taken = 0;
        for (int i = 0; i < n; i++) {
          taken = 0;
          for (int j = 0; j < 32; j++) {
            if ((coins & 1 << j) > 0) {
              if ((a[i] & 1 << j) > 0) {
                taken += 1 << j;
              } else {
                taken -= 1 << j;
              }
            }
          }
          if (taken <= a[i] && taken >= 0) {
            pile = i;
            break;
          }
        }
        out.printf("The computer removes %d coins from pile %d\n", taken, pile);

        a[pile] -= taken;
        sum -= taken;
      }
      computerTurn = !computerTurn;
    }
    out.close();
  }

  static int getSum () {
    int sum = 0;
    for (int i = 0; i < 32; i++) {
      int curr = 0;
      for (int j = 0; j < n; j++) {
        curr += (a[j] & 1 << i) > 0 ? 1 : 0;
      }
      sum += (curr % 2) << i;
    }
    return sum;
  }

  static String next () throws IOException {
    while (st == null || !st.hasMoreTokens())
      st = new StringTokenizer(br.readLine().trim());
    return st.nextToken();
  }

  static long readLong () throws IOException {
    return Long.parseLong(next());
  }

  static int readInt () throws IOException {
    return Integer.parseInt(next());
  }

  static double readDouble () throws IOException {
    return Double.parseDouble(next());
  }

  static char readCharacter () throws IOException {
    return next().charAt(0);
  }

  static String readLine () throws IOException {
    return br.readLine().trim();
  }

}
