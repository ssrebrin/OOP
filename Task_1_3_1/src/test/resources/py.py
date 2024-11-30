# Открываем файл для записи
with open("longRusText.txt", "w", encoding="utf-8") as file:
    # Повторяем строку 1000 раз и записываем в файл
    for _ in range(1000):
        file.write("Привет мир")
