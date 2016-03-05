package Map;

class Node {

  protected int xPosition;
  protected int yPosition;
 

  // Constructor initializes data members.

  protected Node(int x, int y)
  {
    xPosition = x;
    yPosition = y;
  }

 

  // Create a pretty representation
  // Format: [x,y]

  public String toString()
  {
    StringBuilder output = new StringBuilder();
    
    output.append("[");
    output.append(xPosition);
    output.append(",");
    output.append(yPosition);
    output.append("]");
    
    return output.toString();
  }
} 