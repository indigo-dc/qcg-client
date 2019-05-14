package pl.psnc.qcg;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author Piotr
 */
public class QcgNode {
  public String name;
  public int    cpus;
  public String host;
  public int    memory;
  public int    memory_alloc;
  public String state;
  public String queues;

  public QcgNode(String name, JsonNode json) {
    this.name = name;
    for (Iterator<Map.Entry<String, JsonNode>> fields = json.fields(); fields.hasNext(); ) {
      Map.Entry<String, JsonNode> field = fields.next();
      String fieldName = field.getKey();
      JsonNode value = field.getValue();

      if (fieldName.equals("cpus")) this.cpus = value.asInt();
      if (fieldName.equals("host")) this.host = value.asText();
      if (fieldName.equals("memory")) this.memory = value.asInt();
      if (fieldName.equals("memory_alloc")) this.memory_alloc = value.asInt();
      if (fieldName.equals("state")) this.state = value.asText();
      if (fieldName.equals("queues")) this.queues = value.asText();
    }
  }

  @Override
  public String toString() {
    return "Node " + name + "{" +
      "cpus="         + cpus         + " " +
      "host="         + host         + " " +
      "memory="       + memory       + " " +
      "memory_alloc=" + memory_alloc + " " +
      "state="        + state        + " " +
      "queues="       + queues       + "}";
  }

}
