package code.src;
/**
 * A new KMP instance is created for every substring search performed. Both the
 * pattern and the text are passed to the constructor and the search method. You
 * could, for example, use the constructor to create the match table and the
 * search method to perform the search itself.
 */
public class KMP {
  private boolean found = false;
  @SuppressWarnings("unused")
  private String pattern;
  @SuppressWarnings("unused")
  private String text;
  int[] table;

  public KMP(String pattern, String text) {
    this.pattern = pattern;
    this.text = text;

  }

  /**
	 * Perform KMP substring search on the given text with the given pattern.
	 * 
	 * This should return the starting index of the first substring match if it
	 * exists, or -1 if it doesn't.
	 */
  public int searchKMP(String pattern, String text) {
    int[] matchTable = matchTable(pattern);
    int pLen = pattern.length();
    int tLen = text.length();
    int pIndex = 0;
    int tIndex = 0;
    long startTime = System.currentTimeMillis();
    while (tIndex + pIndex < tLen) {
      if (pattern.charAt(pIndex) == text.charAt(tIndex + pIndex)) {
        pIndex++;
        if (pIndex == pLen) {
          return tIndex;
        }

      } else if (matchTable[pIndex] == -1) {
        pIndex = 0;
        tIndex = tIndex + pIndex + 1;
      }
      else {
        tIndex = tIndex + pIndex - matchTable[pIndex];
        pIndex = matchTable[pIndex];
      }
      long endTime = System.currentTimeMillis();
      System.out.println(endTime - startTime);
    }
    return -1;
  }


  /**
   * Brute force search.
   * @param pattern
   * @param text
   * @return
   */
  public int searchBruteForce(String pattern, String text) {
    if (pattern.length() == 0 || text.length() == 0) {
      return -1;
    }
    long startTime = System.currentTimeMillis();

    for (int i = 0; i < text.length() - pattern.length() + 1; i++) {
      found = true;

      for (int j = 0; j < pattern.length(); j++) {
        if (pattern.charAt(j) != text.charAt(i + j)) {
          found = false;
          break;
        }
      }
      if (found) {
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
        return i;
      }
    }
    return -1;
  }

  
  /**
   * Boyer Morore Search
   * @param pattern
   * @param text
   * @return
   */
  public int searchBoyerMoore(String pattern, String text) {
    //https://en.wikipedia.org/wiki/Boyer%E2%80%93Moore_string-search_algorithm
    if(pattern.length() == 0) {
      return -1;
    }
    int charTable[] = charTable(pattern);
    int offsetTable[] = makeOffsetTable(pattern);
    for (int i = pattern.length() - 1, j; i < text.length();) {
      for (j = pattern.length() - 1; pattern.charAt(j) == text.charAt(i); --i, --j) {
        if (j == 0) {
          return i;
        }
      }

      i += Math.max(offsetTable[pattern.length() - 1 - j], charTable[text.charAt(i)]);
    }
    return -1;
  }

  
  /**
   * Creates match table for KMP
   * @param pattern
   * @return
   */
  private static int[] matchTable(String pattern) {

    if (pattern.length() == 0) {
      return null;
    } else if (pattern.length() == 1) {
      return new int[] { -1 };
    }

    int p = pattern.length();
    int[] matchTable = new int[p];
    matchTable[0] = -1;
    matchTable[1] = 0;
    int index = 0;
    int pos = 2;

    while (pos < p) {
      if (pattern.charAt(pos - 1) == pattern.charAt(index)) {
        matchTable[pos] = index + 1;
        pos++;
        index++;
      } else if (index > 0) {
        index = matchTable[index];
      } else {
        matchTable[pos] = 0;
        pos++;
      }
    }

    return matchTable;
  }
  
  /**
   * Creates char table 
   * @param pattern
   * @return
   */
  private static int[] charTable(String pattern) {
    final int characters = Character.MAX_VALUE + 1;
    int[] table = new int[characters];
    for (int i = 0; i < table.length; ++i) {
      table[i] = pattern.length();
    }
    for (int i = 0; i < pattern.length() - 1; ++i) {
      table[pattern.charAt(i)] = pattern.length() - 1 - i;
    }
    return table;
  }

  /**
   * Gets bad characters 
   * @param pattern
   * @return
   */
  private static int[] makeOffsetTable(String pattern) {
    int[] table = new int[pattern.length()];
    int lastPrefixPosition = pattern.length();
    for (int i = pattern.length(); i > 0; --i) {
      if (isPrefix(pattern, i)) {
        lastPrefixPosition = i;
      }
      table[pattern.length() - i] = lastPrefixPosition - i + pattern.length();
    }
    for (int i = 0; i < pattern.length() - 1; ++i) {
      int slen = suffixLength(pattern, i);
      table[slen] = pattern.length() - 1 - i + slen;
    }
    return table;
  }
  
  /**
   * Checks prefix 
   * @param pattern
   * @param x
   * @return
   */
  private static boolean isPrefix(String pattern, int x) {
    for (int i = x, j = 0; i < pattern.length(); ++i, ++j) {
      if (pattern.charAt(i) != pattern.charAt(j)) {
        return false;
      }
    }
    return true;
  }
  
  /**
   * Checks length of search 
   * @param pattern
   * @param x
   * @return
   */
  private static int suffixLength(String pattern, int x) {
    int len = 0;
    for (int i = x, j = pattern.length() - 1;
        i >= 0 && pattern.charAt(i) == pattern.charAt(j); --i, --j) {
      len += 1;
    }
    return len;
  }


}

