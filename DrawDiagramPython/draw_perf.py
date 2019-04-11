#!/usr/bin/env python
# -*- coding: utf-8 -*-

import matplotlib.pyplot as plt
import numpy as np
import os

plt.figure()

# 修改文件路径
top_ab_file = os.getcwd() + "/performance"
x_time = 0

# x轴显示时间标签
x_lable = []
cpu = []
mem = []
i = 0
mincpu = 9999
maxcpu = 0
totalcpu = 0
minmem = 99999
maxmem = 0
totalmem = 0
with open(top_ab_file, 'r') as perf_file:
    line = perf_file.readline()
    while line:
        i += 1
        # 原数据每行数据的条目以","隔开
        x_lable.append(str(line.strip().split(' >> ')[x_time]).strip().split(' ')[0])
        string = str(line.strip().split(' >> ')[1]).strip()
        linecpu = float(string.strip().split(',')[0])
        linemem = round(float(string.strip().split(',')[1]) / 1024.0, 2)
        cpu.append(linecpu)
        mem.append(linemem)
        mincpu = min(linecpu, mincpu)
        maxcpu = max(linecpu, maxcpu)
        minmem = min(linemem, minmem)
        maxmem = max(linemem, maxmem)
        totalcpu += linecpu
        totalmem += linemem
        line = perf_file.readline()

# print("%d,%d"%(len(cpu),len(x_lable)))
# cpu图
x = range(0, i)
# z = np.loadtxt(top_ab_file, delimiter=",", usecols=(x_cpu,), unpack=True)
plt.bar(x, cpu, facecolor='#79b3f2', label='cpu', edgecolor='none')
# 设置x轴显示文本内容
# x轴显示时间标签的个数 （30个）
x_show_num = 30
N = i
if N > x_show_num:
    x_freq = N / x_show_num
    x_show = list(x)[::x_freq]
    x_lable_show = x_lable[::x_freq]
    x_show.append(i - 1)
    x_lable_show.append(x_lable[i - 1])
else:
    x_show = x
    x_lable_show = x_lable
# x轴显示时间的倾斜角
plt.xticks(x_show, x_lable_show, fontsize=10, rotation=90)
plt.subplots_adjust(bottom=0.19)
plt.grid(True)
# x轴标题
plt.xlabel('Time', fontsize=14)
plt.xlim(0, i)
# y轴标题
plt.ylabel('cpu(%)')
plt.ylim(0, maxcpu + 30)
# title
plt.title('performance_cpu')
# 图片文件名
cpu_fig_name = 'cpuBar.png'

col_labels = ['min', 'max', 'average']
row_labels = ['cpu']
table_vals = [[mincpu, maxcpu, round(totalcpu / len(cpu), 3)]]
row_colors = ['lightgrey']
col_colors = ['lightgrey', 'lightgrey', 'lightgrey']
my_table = plt.table(cellText=table_vals, colWidths=[0.1] * 3, rowLabels=row_labels, colLabels=col_labels,
                     rowColours=row_colors, colColours=col_colors, loc='best')
my_table.auto_set_font_size(False)
my_table.set_fontsize(12)
my_table.scale(1.5, 1.2)
plt.savefig(os.getcwd() + '/' + cpu_fig_name)
plt.close()

# mem图
# y = np.loadtxt(top_ab_file, delimiter=",", usecols=(x_mem,), unpack=True)
# 将获取的mem KB转成MB，保留两位小数
# for item in range(0, i):
#    mem[item] = round(mem[item], 2)
plt.bar(x, mem, facecolor='#79b3f2', label='mem', edgecolor='none')
# plt.plot(x, y, color='g', label='mem')
# x轴显示时间的倾斜角
plt.xticks(x_show, x_lable_show, fontsize=10, rotation=90)
plt.subplots_adjust(bottom=0.19)
plt.grid(True)
# x轴标题
plt.xlabel('Time')
plt.xlim(0, i)
# y轴标题
plt.ylabel('Mem(MB)')
plt.ylim(0, maxmem + 300)
# title
plt.title('performance_memory')
# 图片文件名
mem_fig_name = 'memoryBar.png'

table_vals = [[minmem, maxmem, round(totalmem / len(mem), 3)]]
row_labels = ['mem']
my_table = plt.table(cellText=table_vals, colWidths=[0.1] * 3, rowLabels=row_labels, colLabels=col_labels,
                     rowColours=row_colors, colColours=col_colors, loc='best')
my_table.auto_set_font_size(False)
my_table.set_fontsize(12)
my_table.scale(1.5, 1.2)

plt.savefig(os.getcwd() + '/' + mem_fig_name)
plt.close()
