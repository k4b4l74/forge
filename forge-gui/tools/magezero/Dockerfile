FROM python:3.12-slim
ARG TORCH_INDEX_URL=https://download.pytorch.org/whl/cpu
RUN pip install --no-cache-dir torch==2.6.0 --index-url ${TORCH_INDEX_URL}
WORKDIR /app
COPY bridge.py /app/bridge.py
ENV OMP_NUM_THREADS=2 MKL_NUM_THREADS=2 PYTHONUNBUFFERED=1
ENTRYPOINT ["python", "/app/bridge.py"]
