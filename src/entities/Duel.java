package entities;

import java.sql.Timestamp;

public class Duel {

	int id;
	int idUser1;
	int idUser2;
	Timestamp playDate;
	int idWinner;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdUser1() {
		return idUser1;
	}

	public void setIdUser1(int idUser1) {
		this.idUser1 = idUser1;
	}

	public int getIdUser2() {
		return idUser2;
	}

	public void setIdUser2(int idUser2) {
		this.idUser2 = idUser2;
	}

	public Timestamp getPlayDate() {
		return playDate;
	}

	public void setPlayDate(Timestamp playDate) {
		this.playDate = playDate;
	}

	public int getIdWinner() {
		return idWinner;
	}

	public void setIdWinner(int idWinner) {
		this.idWinner = idWinner;
	}

	public Duel(int idUser1, int idUser2, int idWinner) {
		super();
		// this.id = id;
		this.idUser1 = idUser1;
		this.idUser2 = idUser2;
		// this.playDate = playDate;
		this.idWinner = idWinner;
	}

	@Override
	public String toString() {
		return "Duel [id=" + id + ", idUser1=" + idUser1 + ", idUser2="
				+ idUser2 + ", playDate=" + playDate + ", idWinner=" + idWinner
				+ "]";
	}
}
