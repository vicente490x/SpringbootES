package its.springboot.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import its.springboot.dto.ClasseDTO;
import its.springboot.model.Classe;

@Service
public class ClasseService {

    private List<Classe> classi = new ArrayList<>();
    private Long nextId = 1L;

    public ClasseService() {
        add("5A", 25);
        add("5B", 28);
        add("5C", 30);
        add("4A", 27);
        add("4B", 26);
        add("4C", 29);
        add("3A", 24);
        add("3B", 22);
        add("3C", 31);
        add("2A", 26);
        add("2B", 27);
        add("2C", 28);
        add("1A", 25);
        add("1B", 24);
        add("1C", 29);
    }

    private void add(String sezione, int numeroStudenti) {
        Classe c = new Classe(sezione, numeroStudenti);
        c.setId(nextId++);
        classi.add(c);
    }

    private ClasseDTO toDTO(Classe c) {
        return new ClasseDTO(c.getId(), c.getSezione(), c.getNumeroStudenti());
    }

    private Classe toEntity(ClasseDTO dto) {
        Classe c = new Classe(dto.getSezione(), dto.getNumeroStudenti());
        c.setId(dto.getId());
        return c;
    }

    public List<ClasseDTO> getClassi() {
        List<ClasseDTO> result = new ArrayList<>();
        for (Classe c : classi) result.add(toDTO(c));
        return result;
    }

    public ClasseDTO getClasse(Long id) {
        for (Classe c : classi)
            if (c.getId().equals(id))
                return toDTO(c);
        return null;
    }

    public ClasseDTO insertClasse(ClasseDTO dto) {
        Classe c = toEntity(dto);
        c.setId(nextId++);
        classi.add(c);
        return toDTO(c);
    }

    public List<ClasseDTO> insertListClasse(List<ClasseDTO> dtos) {
        List<ClasseDTO> result = new ArrayList<>();
        for (ClasseDTO dto : dtos)
            result.add(insertClasse(dto));
        return result;
    }

    public ClasseDTO updateClasse(Long id, ClasseDTO dto) {
        for (Classe c : classi) {
            if (c.getId().equals(id)) {
                c.setSezione(dto.getSezione());
                c.setNumeroStudenti(dto.getNumeroStudenti());
                return toDTO(c);
            }
        }
        return null;
    }

    public boolean deleteClasse(Long id) {
        return classi.removeIf(c -> c.getId().equals(id));
    }
}
