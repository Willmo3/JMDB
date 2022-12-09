package cast;

import java.util.ArrayList;
import java.util.List;

/**
 * A class representing a general job that has workers.
 * 
 * @author Matthew Potter
 * @version 12/09/2022
 */
public class Job
{
  private String job;
  private ArrayList<Worker> workers;

  /**
   * Constructs a job with the specified job title.
   * 
   * @param job
   *          the job title that this job specifies
   */
  public Job(String job)
  {
    this.job = job;
    workers = new ArrayList<Worker>();
  }

  /**
   * Adds a worker to the job.
   * 
   * @param worker
   *          the worker to add to this job
   */
  public void add(Worker worker)
  {
    workers.add(worker);
  }

  /**
   * Getter for the job title.
   * 
   * @return the job title
   */
  public String getJob()
  {
    return job;
  }
  
  /**
   * Getter for the list of workers in this job.
   * 
   * @return the list of workers in this job.
   */
  public List<Worker> getWorkers()
  {
    return workers;
  }
}
