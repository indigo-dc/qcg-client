package pl.psnc.qcg;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import java.io.IOException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;

/**
 *
 * @author Piotr
 */
public class QcgApi {
  private static final String URL = "http://qcg.lambda.ara.app.test.k8s.apps.psnc.pl/api/";
  private static final String token = "*** insert token here ***";

  private int status;    // status of last GET/POST request
  private String output; // output of last GET/POST request

  // information about available resources
  public QcgResources GetResources() {
    QcgGet("resources");
    if (status==200) return new QcgResources(toJson(output));
    System.err.println("Can't get resources!");
    throw new QcgException("Can't get resources! Request exited with status "+status);
  }

  // start job
  public QcgJob StartJob(String command, boolean dontRemoveDirectory) {
    ObjectMapper mapper = new ObjectMapper();
    JsonNode jobNode = mapper.createObjectNode();
    ObjectNode executionNode = ((ObjectNode)jobNode).putObject("execution");
    executionNode.put("executable", command);
    if (dontRemoveDirectory) {
      ObjectNode directoryPolicyNode = executionNode.putObject("directory_policy");
      directoryPolicyNode.put("remove", "NEVER");
    }
    String job = jobNode.toString();
    QcgPost("jobs/", job);
    if (status==201) return new QcgJob(toJson(output));
    System.err.println("Can't start job!");
    throw new QcgException("Can't start job! Request exited with status "+status);
  }

  // get job list
  public QcgJobs GetJobs() {
    QcgGet("jobs");
    if (status==200) return new QcgJobs(toJson(output));
    System.err.println("Can't get jobs!");
    throw new QcgException("Can't get jobs! Request exited with status "+status);
  }

  // get information about job
  public QcgJob GetJobInfo(int jobId) {
    QcgGet("jobs/"+jobId);
    if (status==200) return new QcgJob(toJson(output));
    System.err.println("Can't get job info!");
    throw new QcgException("Can't get job info! Request exited with status "+status);
  }

  private int QcgGet(String path) {
    return QcgGet(path, null);
  }

  private int QcgGet(String path, MultivaluedMap params) {
    try {
      Client client = Client.create();
      WebResource webResource = client.resource(URL+path);
      if (params!=null) webResource = webResource.queryParams(params);
      ClientResponse response = webResource
        .accept("application/json")
        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
        .get(ClientResponse.class);
      status = response.getStatus();
      //System.out.println("status: "+status);
      output = status==204 ? "" : response.getEntity(String.class);
      //System.out.println("output: "+output);
      return status;
    } catch(Exception e) {
      System.err.println("Exception: "+e.getMessage());
      //e.printStackTrace();
      throw new QcgException("Can't make GET request!");
    }
  }

  private int QcgPost(String path, String json) {
    return QcgPost(path, json, null);
  }

  private int QcgPost(String path, Object body, MultivaluedMap params) {
    try {
      Client client = Client.create();
      WebResource webResource = client.resource(URL+path);
      if (params!=null) webResource = webResource.queryParams(params);
      ClientResponse response = webResource
        .accept("application/json")
        .type("application/json")
        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
        .post(ClientResponse.class, body);
      status = response.getStatus();
      //System.out.println("status: "+status);
      output = status==204 ? "" : response.getEntity(String.class);
      //System.out.println("output: "+output);
      return status;
    } catch(Exception e) {
      System.err.println("Exception: "+e.getMessage());
      //e.printStackTrace();
      throw new QcgException("Can't make POST request!");
    }
  }

  // convert json string to json object
  private JsonNode toJson(String s) {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      return objectMapper.readTree(s);
    } catch(IOException e) {
      throw new QcgException("Error converting json string to json object!");
    }
  }

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    try {
      System.out.println("Hello!");
      QcgApi qcgApi = new QcgApi();
      qcgApi.testApi();
      System.out.println("Bye...");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void testApi() throws QcgException {
    System.out.println("***Get resources");
    QcgResources resources = GetResources();
    System.out.println("resources:\n"+resources.toString());

    System.out.println("***Start job");
    QcgJob newJob = StartJob("/usr/bin/date", true);
    System.out.println("new job:\n"+newJob.toString());

    System.out.println("***Get jobs");
    QcgJobs jobs = GetJobs();
    for (QcgJob job : jobs.list) {
      System.out.println(job.toString());
    }

    System.out.println("***Get info about job 4");
    QcgJob job4 = GetJobInfo(4);
    System.out.println("job 4:\n"+job4.toString());
  }

}