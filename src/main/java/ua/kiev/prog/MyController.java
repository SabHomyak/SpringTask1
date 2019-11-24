package ua.kiev.prog;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.reflect.generics.scope.Scope;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.spi.http.HttpContext;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/")
public class MyController {

    private Map<Long, MultipartFile> photos = new HashMap<>();

    @RequestMapping("/")
    public String onIndex() {
        return "index";
    }

    @RequestMapping(value = "/add_photo", method = RequestMethod.POST)
    public String onAddPhoto(Model model, @RequestParam MultipartFile photo) {
        if (photo.isEmpty())
            throw new PhotoErrorException();
        try {
            long id = System.currentTimeMillis();
            photos.put(id, photo);
            if (photo.isEmpty()) {
                throw new IOException();
            }
            model.addAttribute("photo_id", id);
            return "result";
        } catch (IOException e) {
            throw new PhotoErrorException();
        }
    }

    @RequestMapping("/photo/{photo_id}")
    public ResponseEntity<byte[]> onPhoto(@PathVariable("photo_id") long id) {
        return photoById(id);
    }

    @RequestMapping(value = "/view", method = RequestMethod.POST)
    public ResponseEntity<byte[]> onView(@RequestParam("photo_id") long id) {
        return photoById(id);
    }

    @RequestMapping("/delete/{photoId}")
    public String onDelete(@PathVariable("photoId") long id) {
        if (photos.remove(id) == null)
            throw new PhotoNotFoundException();
        else
            return "index";
    }

    @RequestMapping("/list")
    public String onList(Model model) {
        model.addAttribute("photos", photos);
        return "list";
    }

    @RequestMapping("/listDelete")
    public String onListDelete(HttpServletRequest request, Model model) {
        Set<Map.Entry<String, String[]>> photosId = request.getParameterMap().entrySet();
        for (Map.Entry photoId : photosId) {
            String[] id = (String[]) photoId.getValue();
            if (photos.remove(Long.valueOf(id[0])) == null) {
                throw new PhotoNotFoundException();
            }
        }
        model.addAttribute("photos", photos);
        return "list";
    }

    private ResponseEntity<byte[]> photoById(long id) {
        MultipartFile file = photos.get(id);
        byte[] bytes;
        try {
            bytes = file.getBytes();
        } catch (IOException e) {
            throw new PhotoNotFoundException();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);

        return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
    }
}
