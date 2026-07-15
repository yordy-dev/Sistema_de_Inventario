package repositorios.memoria;

import java.util.ArrayList;
import java.util.List;
import modelos.Categoria;
import repositorios.ICategoriaRepository;

public class CategoriaRepositoryMemoria implements ICategoriaRepository {
    private static List<Categoria> categorias = new ArrayList<>();
    private static CategoriaRepositoryMemoria instancia;
    private static int ultimoId = 5;

    static {
        categorias.add(new Categoria(1, "Electrodomésticos", true));
        categorias.add(new Categoria(2, "Cocina", true));
        categorias.add(new Categoria(3, "Congeladores", true));
        categorias.add(new Categoria(4, "Televisores", true));
        categorias.add(new Categoria(5, "Aire Acondicionado", true));
    }

    private CategoriaRepositoryMemoria() {}

    public static synchronized CategoriaRepositoryMemoria getInstancia() {
        if (instancia == null) {
            instancia = new CategoriaRepositoryMemoria();
        }
        return instancia;
    }

    @Override
    public List<Categoria> listar() {
        List<Categoria> activas = new ArrayList<>();
        for (Categoria c : categorias) {
            if (c.isEstado()) {
                activas.add(c);
            }
        }
        return activas;
    }

    @Override
    public Categoria buscarPorId(int id) {
        for (Categoria c : categorias) {
            if (c.getIdCategoria() == id) {
                return c;
            }
        }
        return null;
    }

    @Override
    public boolean registrar(Categoria categoria) {
        ultimoId++;
        categoria.setIdCategoria(ultimoId);
        categorias.add(categoria);
        return true;
    }

    @Override
    public boolean editar(Categoria categoria) {
        for (int i = 0; i < categorias.size(); i++) {
            if (categorias.get(i).getIdCategoria() == categoria.getIdCategoria()) {
                categorias.set(i, categoria);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean eliminar(int id) {
        Categoria c = buscarPorId(id);
        if (c != null) {
            c.setEstado(false);
            return true;
        }
        return false;
    }
}
