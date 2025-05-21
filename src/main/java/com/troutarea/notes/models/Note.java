package com.troutarea.notes.models;

public class Note {
    private int id;
    private String tour;
    private String lure;
    private String lureDesc;
    private String descriptionWiring;
    private String fishActivity;
    private String horizon;
    private String waterClarity;
    private String weather;
    private String castDistance;
    private String fishingGear;
    private int sector;
    private String date;

    // Конструкторы
    public Note() {
        // Пустой конструктор
    }


    public Note(int id, String tour, String lure, String lureDesc,
                String descriptionWiring, String fishActivity, String horizon,
                String waterClarity, String weather, String castDistance,
                String fishingGear, int sector, String date) {
        this.id = id;
        this.tour = tour;
        this.lure = lure;
        this.lureDesc = lureDesc;
        this.descriptionWiring = descriptionWiring;
        this.fishActivity = fishActivity;
        this.horizon = horizon;
        this.waterClarity = waterClarity;
        this.weather = weather;
        this.castDistance = castDistance;
        this.fishingGear = fishingGear;
        this.sector = sector;
        this.date = date;
    }



    // Геттеры и сеттеры
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTour() { return tour; }
    public void setTour(String tour) { this.tour = tour; }

    public String getLure() { return lure; }
    public void setLure(String lure) { this.lure = lure; }

    public String getLureDesc() { return lureDesc; }
    public void setLureDesc(String lureDesc) { this.lureDesc = lureDesc; }

    public String getDescriptionWiring() { return descriptionWiring; }
    public void setDescriptionWiring(String descriptionWiring) { this.descriptionWiring = descriptionWiring; }

    public String getFishActivity() { return fishActivity; }
    public void setFishActivity(String fishActivity) { this.fishActivity = fishActivity; }

    public String getHorizon() { return horizon; }
    public void setHorizon(String horizon) { this.horizon = horizon; }

    public String getWaterClarity() { return waterClarity; }
    public void setWaterClarity(String waterClarity) { this.waterClarity = waterClarity; }

    public String getWeather() { return weather; }
    public void setWeather(String weather) { this.weather = weather; }

    public String getCastDistance() { return castDistance; }
    public void setCastDistance(String castDistance) { this.castDistance = castDistance; }

    public String getFishingGear() { return fishingGear; }
    public void setFishingGear(String fishingGear) { this.fishingGear = fishingGear; }

    public int getSector() { return sector; }
    public void setSector(int sector) { this.sector = sector; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }



    // Метод toString для удобного вывода информации о заметке
    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +

                ", tour='" + tour + '\'' +
                ", lure='" + lure + '\'' +
                ", sector=" + sector +
                ", date='" + date + '\'' +
                '}';
    }

    // Метод equals для сравнения объектов
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Note note = (Note) o;

        if (id != note.id) return false;

        if (sector != note.sector) return false;
        if (tour != null ? !tour.equals(note.tour) : note.tour != null) return false;
        if (lure != null ? !lure.equals(note.lure) : note.lure != null) return false;
        if (lureDesc != null ? !lureDesc.equals(note.lureDesc) : note.lureDesc != null) return false;
        if (descriptionWiring != null ? !descriptionWiring.equals(note.descriptionWiring) : note.descriptionWiring != null)
            return false;
        if (fishActivity != null ? !fishActivity.equals(note.fishActivity) : note.fishActivity != null)
            return false;
        if (horizon != null ? !horizon.equals(note.horizon) : note.horizon != null) return false;
        if (waterClarity != null ? !waterClarity.equals(note.waterClarity) : note.waterClarity != null)
            return false;
        if (weather != null ? !weather.equals(note.weather) : note.weather != null) return false;
        if (castDistance != null ? !castDistance.equals(note.castDistance) : note.castDistance != null)
            return false;
        if (fishingGear != null ? !fishingGear.equals(note.fishingGear) : note.fishingGear != null)
            return false;
        return date != null ? date.equals(note.date) : note.date == null;
    }

    // Метод hashCode для использования в коллекциях
    @Override
    public int hashCode() {
        int result = id;

        result = 31 * result + (tour != null ? tour.hashCode() : 0);
        result = 31 * result + (lure != null ? lure.hashCode() : 0);
        result = 31 * result + (lureDesc != null ? lureDesc.hashCode() : 0);
        result = 31 * result + (descriptionWiring != null ? descriptionWiring.hashCode() : 0);
        result = 31 * result + (fishActivity != null ? fishActivity.hashCode() : 0);
        result = 31 * result + (horizon != null ? horizon.hashCode() : 0);
        result = 31 * result + (waterClarity != null ? waterClarity.hashCode() : 0);
        result = 31 * result + (weather != null ? weather.hashCode() : 0);
        result = 31 * result + (castDistance != null ? castDistance.hashCode() : 0);
        result = 31 * result + (fishingGear != null ? fishingGear.hashCode() : 0);
        result = 31 * result + sector;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }

}

