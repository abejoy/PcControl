import pandas as pd
from datetime import date

myHeader = ["name", "email", "contactNum", "parentContactNum" ,"dob", "age", "registeredDate", "unitName"]

def write(df):
    writer = pd.ExcelWriter('test.xls', engine='openpyxl')
    df.to_excel(writer, sheet_name='welcome', index=False, header=myHeader)
    writer.save()

def read():
    return pd.read_excel('test.xls')

def addRow(myrow):
    try:
        df = read()
    except FileNotFoundError:
        df = pd.DataFrame(columns=myHeader)
    except:
        print("Something else went wrong")
    

    if df['email'].str.contains(myrow[1]).any():
        raise ValueError('Email already registered')

    df.loc[len(df)] = myrow
    write(df)






def main():
    today = date.today()
    addRow(myrow = ["tommy", "tomfmy@gmail.com", "07744333222", "0553534342", "24/02/1998", 24, today, "NWLU"]);
    print(read())

if __name__ == "__main__":
    main()