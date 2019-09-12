package cn.tedu.cloud_note.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.tedu.cloud_note.dao.NoteDao;
import cn.tedu.cloud_note.entity.Note;
import cn.tedu.cloud_note.util.NoteResult;
import cn.tedu.cloud_note.util.NoteUtil;

@Service("noteService")
public class NoteServiceImpl implements NoteService {

	@Resource
	private NoteDao noteDao;

	public NoteResult<List<Map>> loadBookNotes(String bookId) {
		List<Map> list = noteDao.findByBookId(bookId);

		NoteResult<List<Map>> result = new NoteResult<List<Map>>();
		result.setStatus(0);
		result.setMsg("�ʼǼ��سɹ�");
		result.setData(list);

		return result;
	}

	public NoteResult<Note> loadNote(String noteId) {
		Note note = noteDao.findByNoteId(noteId);

		NoteResult<Note> result = new NoteResult<Note>();

		if (note == null) {
			result.setStatus(1);
			result.setMsg("δ�ҵ�����");
			return result;
		} else {
			result.setStatus(0);
			result.setMsg("���ظ����ʼǳɹ�");
			result.setData(note);
			return result;
		}
	}

	public NoteResult<Note> updateNote(Note note) {
		System.out.println(note.getCn_note_title());
		int n = noteDao.updateNote(note);
		NoteResult<Note> result = new NoteResult<Note>();

		if (n == 1) {
			Note newNote = noteDao.findByNoteId(note.getCn_note_id());

			result.setStatus(0);
			result.setMsg("���³ɹ�");
			result.setData(newNote);

			return result;
		}
		return result;
	}

	
	
	public NoteResult<Object> updateNote(String noteId, String title, String body) {
		Note note = new Note();
		note.setCn_note_id(noteId);
		note.setCn_note_title(title);
		note.setCn_note_body(body);
		note.setCn_note_last_modify_time(System.currentTimeMillis());

		int rows = noteDao.updateNote(note);
		NoteResult<Object> result = new NoteResult<Object>();
		
		if (rows == 1) {
			result.setStatus(0);
			result.setMsg("���³ɹ�");
			return result;
		}else{
			result.setStatus(1);
			result.setMsg("����ʧ��");
			return result;
		}
	}

	public NoteResult<Note> addNote(String userId, String bookId, String title) {
		Note note = new Note();
		
		//�������Ĳ���
		note.setCn_user_id(userId);
		note.setCn_notebook_id(bookId);
		note.setCn_note_title(title);
		
		//�Զ����ɵĲ���
		note.setCn_note_create_time(System.currentTimeMillis());
		note.setCn_note_last_modify_time(System.currentTimeMillis());
		note.setCn_note_status_id("1");
		note.setCn_note_id(NoteUtil.createId());
		note.setCn_note_body("");
		note.setCn_note_type_id("1");
		
		noteDao.save(note);
		
		NoteResult<Note> result = new NoteResult<Note>();
		result.setStatus(0);
		result.setMsg("�ʼǴ����ɹ�");
		result.setData(note);
		
		return result;
	}

}
