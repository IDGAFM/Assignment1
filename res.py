import sys
import pandas as pd
import matplotlib.pyplot as plt
import os

def plot_csv(file_path):
    # 🔍 Проверяем, существует ли файл
    if not os.path.exists(file_path):
        print(f"❌ Файл не найден: {file_path}")
        sys.exit(1)

    # Загружаем CSV
    df = pd.read_csv(file_path)

    # Проверяем колонки
    if "trial" not in df.columns or "time_ms" not in df.columns:
        print("❌ Ошибка: В CSV нет колонок 'trial' и 'time_ms'")
        print("Колонки в CSV:", list(df.columns))
        sys.exit(1)

    # Создаем папку для картинок
    os.makedirs("images", exist_ok=True)

    # Имя алгоритма = первая строка в колонке algo
    algo = df["algo"].iloc[0] if "algo" in df.columns else "algorithm"

    # Имя картинки
    img_path = f"images/{algo}_performance.png"

    # Строим график
    plt.figure(figsize=(10, 6))
    plt.plot(df['trial'], df['time_ms'], marker='o', linestyle='-', color='b')
    plt.title(f"Algorithm Performance: {algo}")
    plt.xlabel("Trial")
    plt.ylabel("Time (ms)")
    plt.grid(True)
    plt.savefig(img_path)
    plt.close()

    print(f"✅ График сохранен: {img_path}")

    # Добавляем картинку в README.md
    readme_path = "README.md"
    md_line = f"\n### {algo.capitalize()} Performance\n\n![{algo}]({img_path})\n"

    if os.path.exists(readme_path):
        with open(readme_path, "a", encoding="utf-8") as f:
            f.write(md_line)
    else:
        with open(readme_path, "w", encoding="utf-8") as f:
            f.write(f"# Algorithm Results\n{md_line}")

    print(f"✅ Картинка добавлена в {readme_path}")


if __name__ == "__main__":
    if len(sys.argv) < 2:
        print("Usage: py -3 res.py <csv_file>")
        sys.exit(1)

    csv_file = sys.argv[1]
    plot_csv(csv_file)
