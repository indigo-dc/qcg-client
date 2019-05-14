package pl.psnc.qcg;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author Piotr
 */
public class QcgJob {
  public String id;
  public String attributes; // ??
  public String user;
  public String state;
  public String operation;
  public String note;

  public String directory;
  public String executable;
  public String environment_user;
  public String job_id;
  public String job_secret_auth;

  public String operation_start;
  public String resource;
  public String queue;
  public String local_user;
  public String local_group;
  public String local_id;
  public String submit_time;
  public String start_time;
  public String finish_time;
  public String updated_time;
  public String eta;
  public String nodes;
  public int    cpus;
  public int    exit_code;
  public String errors;
  public int    resubmit;
  public String work_dir;

  public QcgJob(JsonNode json) {
    for (Iterator<Map.Entry<String, JsonNode>> fields = json.fields(); fields.hasNext(); ) {
      Map.Entry<String, JsonNode> field = fields.next();
      String fieldName = field.getKey();
      JsonNode value = field.getValue();

      if (fieldName.equals("id")) this.id = value.asText();
      if (fieldName.equals("user")) this.user = value.asText();
      if (fieldName.equals("state")) this.state = value.asText();
      if (fieldName.equals("operation")) this.operation = value.asText();
      if (fieldName.equals("note")) this.note = value.asText();

      if (fieldName.equals("description")) getDescriptionFromJson(value);

      if (fieldName.equals("operation_start")) this.operation_start = value.asText();
      if (fieldName.equals("resource")) this.resource = value.asText();
      if (fieldName.equals("queue")) this.queue = value.asText();
      if (fieldName.equals("local_user")) this.local_user = value.asText();
      if (fieldName.equals("local_group")) this.local_group = value.asText();
      if (fieldName.equals("local_id")) this.local_id = value.asText();
      if (fieldName.equals("submit_time")) this.submit_time = value.asText();
      if (fieldName.equals("start_time")) this.start_time = value.asText();
      if (fieldName.equals("finish_time")) this.finish_time = value.asText();
      if (fieldName.equals("updated_time")) this.updated_time = value.asText();
      if (fieldName.equals("eta")) this.eta = value.asText();
      if (fieldName.equals("nodes")) this.nodes = value.asText();
      if (fieldName.equals("cpus")) this.cpus = value.asInt();
      if (fieldName.equals("exit_code")) this.exit_code = value.asInt();
      if (fieldName.equals("errors")) this.errors = value.asText();
      if (fieldName.equals("resubmit")) this.resubmit = value.asInt();
      if (fieldName.equals("work_dir")) this.work_dir = value.asText();
    }
  }

  private void getDescriptionFromJson(JsonNode json) {
    for (Iterator<Map.Entry<String, JsonNode>> fields = json.fields(); fields.hasNext(); ) {
      Map.Entry<String, JsonNode> field = fields.next();
      String fieldName = field.getKey();
      JsonNode value = field.getValue();
      if (fieldName.equals("execution")) getExecutionFromJson(value);
    }
  }

  private void getExecutionFromJson(JsonNode json) {
    for (Iterator<Map.Entry<String, JsonNode>> fields = json.fields(); fields.hasNext(); ) {
      Map.Entry<String, JsonNode> field = fields.next();
      String fieldName = field.getKey();
      JsonNode value = field.getValue();
      if (fieldName.equals("directory")) this.directory = value.asText();
      if (fieldName.equals("executable")) this.executable = value.asText();
      if (fieldName.equals("environment")) getEnvironmentFromJson(value);
    }
  }

  private void getEnvironmentFromJson(JsonNode json) {
    for (Iterator<Map.Entry<String, JsonNode>> fields = json.fields(); fields.hasNext(); ) {
      Map.Entry<String, JsonNode> field = fields.next();
      String fieldName = field.getKey();
      JsonNode value = field.getValue();
      if (fieldName.equals("USER")) this.environment_user = value.asText();
      if (fieldName.equals("QCGNCOMP_JOB_ID")) this.job_id = value.asText();
      if (fieldName.equals("QCGNCOMP_JOB_SECRET_AUTH")) this.job_secret_auth = value.asText();
    }
  }

  @Override
  public String toString() {
    return "Job {" +
      "id="               + id                  + " " +
      //"attributes="
      "user="             + user                + " " +
      "state="            + state               + " " +
      "operation="        + operation           + " " +
      "note="             + note                + " " +
      "directory="        + directory           + " " +
      "executable="       + executable          + " " +
      "environment_user=" + environment_user    + " " +
      "job_id="           + job_id              + " " +
      "job_secret_auth="  + job_secret_auth     + " " +
      "operation_start="  + operation_start     + " " +
      "resource="         + resource            + " " +
      "queue="            + queue               + " " +
      "local_user="       + local_user          + " " +
      "local_group="      + local_group         + " " +
      "local_id="         + local_id            + " " +
      "submit_time="      + submit_time         + " " +
      "start_time="       + start_time          + " " +
      "finish_time="      + finish_time         + " " +
      "updated_time="     + updated_time        + " " +
      "eta="              + eta                 + " " +
      "nodes="            + nodes               + " " +
      "cpus="             + cpus                + " " +
      "exit_code="        + exit_code           + " " +
      "errors="           + errors              + " " +
      "resubmit="         + resubmit            + " " +
      "work_dir="         + work_dir            + "}";
  }

}