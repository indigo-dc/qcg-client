package pl.psnc.qcg;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Piotr
 */
public class QcgNodes {
  public List<QcgNode> list;
  public int totalNodes;
  public int totalCpus;
  public int totalMemory;
  public int statesIdle;

  public QcgNodes(JsonNode json) {
    for (Iterator<Map.Entry<String, JsonNode>> fields = json.fields(); fields.hasNext(); ) {
      Map.Entry<String, JsonNode> field = fields.next();
      String name = field.getKey();
      JsonNode value = field.getValue();

      if (name.equals("list")) getNodesFromJson(value);
      if (name.equals("node_stats")) getNodeStatsFromJson(value);
    }
  }

  private void getNodesFromJson(JsonNode json) {
    list = new ArrayList<>();
    for (Iterator<Map.Entry<String, JsonNode>> fields = json.fields(); fields.hasNext(); ) {
      Map.Entry<String, JsonNode> field = fields.next();
      String name = field.getKey();
      JsonNode value = field.getValue();
      list.add(new QcgNode(name, value));
    }
  }

  private void getNodeStatsFromJson(JsonNode json) {
    for (Iterator<Map.Entry<String, JsonNode>> fields = json.fields(); fields.hasNext(); ) {
      Map.Entry<String, JsonNode> field = fields.next();
      String name = field.getKey();
      JsonNode value = field.getValue();

      if (name.equals("total")) getTotalsFromJson(value);
      if (name.equals("states")) getStatesFromJson(value);
    }
  }

  private void getTotalsFromJson(JsonNode json) {
    for (Iterator<Map.Entry<String, JsonNode>> fields = json.fields(); fields.hasNext(); ) {
      Map.Entry<String, JsonNode> field = fields.next();
      String name = field.getKey();
      JsonNode value = field.getValue();

      if (name.equals("nodes")) this.totalNodes = value.asInt();
      if (name.equals("cpus")) this.totalCpus = value.asInt();
      if (name.equals("memory")) this.totalMemory = value.asInt();
    }
  }

  private void getStatesFromJson(JsonNode json) {
    for (Iterator<Map.Entry<String, JsonNode>> fields = json.fields(); fields.hasNext(); ) {
      Map.Entry<String, JsonNode> field = fields.next();
      String name = field.getKey();
      JsonNode value = field.getValue();

      if (name.equals("IDLE")) this.statesIdle = value.asInt();
    }
  }

  @Override
  public String toString() {
    String s = "{Nodes:[";
    for (QcgNode node : list) s += node.toString();
    return s + "] " +
      "totalNodes="  + totalNodes  + " " +
      "totalCpus="   + totalCpus   + " " +
      "totalMemory=" + totalMemory + " " +
      "statesIdle="  + statesIdle  + "}";
  }

}
