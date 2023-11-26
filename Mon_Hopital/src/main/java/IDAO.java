import java.time.LocalDate;
import java.util.List;

public interface IDAO<T> {
	public void add(T D) throws Exception;
	public void update(T D);
    public List<T> getAll();


	

}